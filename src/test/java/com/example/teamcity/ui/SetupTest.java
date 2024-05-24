package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SetupTest {
    @BeforeMethod
    public void setup() {
        Configuration.baseUrl = "http://" + Config.getProperty("hostAndPortForSetUp");
        Configuration.remote = "http://localhost/:4444/wd/hub";
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";
        BrowserSettings.setup(Config.getProperty("browserForSetUp")); // firefox by default - hardcoded in .ps1 script
    }

    @Test
    public void startUpTest() {
        new StartUpPage()
                .open()
                .setupTeamcityServer()
                .getHeader()
                .shouldHave(Condition.text("Create Administrator Account"));
    }
}
