package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {

    @Test
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
