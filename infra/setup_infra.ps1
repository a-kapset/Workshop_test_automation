# Перемещение на уровень выше
Set-Location ..

# Установка переменных
$teamcityTestsDirectory = Get-Location
$workdir = "teamcity_tests_infrastructure"
$teamcityServerWorkdir = "teamcity_server"
$teamcityServerAgent = "teamcity_agent"
$selenoidWorkdir = "selenoid"
$teamcityServerContainerName = "teamcity_server_instance"
$teamcityAgentContainerName = "teamcity_agent_instance"
$selenoidContainerName = "selenoid_instance"
$selenoidUIContainerName = "selenoid_ui_instance"

$workdirNames = @($teamcityServerWorkdir, $teamcityServerAgent, $selenoidWorkdir)
$containerNames = @($teamcityServerContainerName, $teamcityAgentContainerName, $selenoidContainerName, $selenoidUIContainerName)

#################################################
Write-Output "Request IP"
$ips = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object { $_.IPAddress -ne "127.0.0.1" }).IPAddress
$ip = $ips[0]
Write-Output "Current IP: $ip"
$propertiesContentForSetUp = "hostForSetUp=$ip`:8111`n" + "browserForSetUp=firefox"
$propertiesContentForSetUp | Out-File -FilePath "$teamcityTestsDirectory/src/main/resources/config.properties" -Encoding ASCII

#################################################
Write-Output "Delete previous run data"
if (Test-Path -Path $workdir) {
    Remove-Item -Recurse -Force $workdir
}
New-Item -ItemType Directory -Force -Path $workdir
Set-Location $workdir

#################################################
Write-Output "Create working directories"
foreach ($dir in $workdirNames) {
    New-Item -ItemType Directory -Force -Path $dir
}

#################################################
Write-Output "Stop and delete previous run containers"
foreach ($container in $containerNames) {
    docker stop $container
    docker rm $container
}

#################################################
Write-Output "Start teamcity server"
Set-Location $teamcityServerWorkdir
docker run -d --name $teamcityServerContainerName `
    -v "${pwd}/logs:/opt/teamcity/logs" `
    -p 8111:8111 `
    jetbrains/teamcity-server
Write-Output "Teamcity Server is running..."

#################################################
Write-Output "Start selenoid"
Set-Location "..\$selenoidWorkdir"
New-Item -ItemType Directory -Force -Path "config"
Copy-Item "$teamcityTestsDirectory/infra/browsers.json" "config/"
docker run -d `
    --name $selenoidContainerName `
    -p 4444:4444 `
    -v /var/run/docker.sock:/var/run/docker.sock `
    -v "${pwd}/config/:/etc/selenoid/:ro" `
    aerokube/selenoid:latest-release

$imageNames = Get-Content "$pwd/config/browsers.json" | Select-String -Pattern '"image": "' | ForEach-Object { $_ -match '"image": "(.*?)"' | Out-Null; $matches[1] }

Write-Output "Pull browser images: $imageNames"
foreach ($image in $imageNames) {
    docker pull $image
}

#################################################
Write-Output "Start selenoid-ui"
docker run -d --name $selenoidUIContainerName -p 8080:8080 aerokube/selenoid-ui --selenoid-uri "http://$ip:4444"

#################################################
Write-Output "Run setup test to setup teamcity server"
Set-Location $teamcityTestsDirectory
mvn clean test -Dtest=SetupTest#startUpTest

#################################################
Write-Output "Parse superuser token"
$superuserToken = Select-String -Path "$teamcityTestsDirectory/$workdir/$teamcityServerWorkdir/logs/teamcity-server.log" -Pattern "Super user authentication token: [0-9]*" | ForEach-Object { $_ -match "Super user authentication token: ([0-9]*)" | Out-Null; $matches[1] }
Write-Output "Super user token: $superuserToken"

#################################################
Write-Output "Run system tests"
Set-Location $teamcityTestsDirectory
$propertiesContent = "host=$ip`:8111`n" + "superUserToken=$superuserToken`n" + "remote=http://localhost:4444/wd/hub`n" + "browser=firefox"
$propertiesContent | Out-File -FilePath "$teamcityTestsDirectory/src/main/resources/config.properties" -Encoding ASCII

# debug output
Get-Content "$teamcityTestsDirectory/src/main/resources/config.properties"

##################################################
#Write-Output "Run API tests"
#mvn clean test -DsuiteXmlFile=testng-suites/api-suite
#
##################################################
#Write-Output "Run UI tests"
#mvn clean test -DsuiteXmlFile=testng-suites/ui-suite

#################################################
Write-Output "Run all regression tests"
mvn clean test -DsuiteXmlFile=testng-suites/regression

