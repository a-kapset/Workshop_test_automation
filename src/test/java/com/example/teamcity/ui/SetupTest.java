package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.requests.checked.AuthRequest;
import com.example.teamcity.ui.pages.AgentsPage;
import com.example.teamcity.ui.pages.StartUpPage;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})
public class SetupTest {
    @Test
    @Description("Service test for server setup")
    public void startUpTest() {
        Configuration.baseUrl = "http://" + Config.getProperty("hostForSetUp");
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";
        BrowserSettings.setup(Config.getProperty("browserForSetUp"));

        new StartUpPage()
                .open()
                .setupTeamcityServer()
                .getHeader()
                .shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test
    @Description("Service test for agent setup")
    public void setAgent() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));

        new StartUpPage()
                .open()
                .loginAsSuperUser();

        new ProjectsPage().goToAgents();

        new AgentsPage().authorizeAgent();

        AuthRequest.logoutAsSuperUser();
    }
}
