package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})
public class CreateNewProjectTest extends BaseUiTest {

    @Test
    @Description("UI: Authorized User Should Be Able To Create New Project")
    public void authorizedUserShouldBeAbleToCreateNewProject() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";

        loginAsAuthorizedUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupUrlProjectAndSubmit(
                        testData.getNewProjectDescription().getName(),
                        testData.getBuildType().getName());

        new ProjectsPage()
                .open()
                .getSubprojects()
                .stream()
                .reduce((first, second) -> second)
                .get()
                .getHeader()
                .shouldHave(Condition.text(testData.getNewProjectDescription().getName()));
    }
}
