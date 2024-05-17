package com.example.teamcity.ui;

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
                .setupProject(
                        testData.getNewProjectDescription().getName(),
                        testData.getBuildType().getName()
                );

        System.out.println();
    }
}
