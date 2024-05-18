package com.example.teamcity.ui.homework;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import com.example.teamcity.ui.pages.admin.EditProjectPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

public class CreateProjectManuallyTest extends BaseUiTest {

    @Test
    public void newProjectCanBeCreated() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator())
                .createProjectManually(
                        testData.getNewProjectDescription().getName(),
                        testData.getNewProjectDescription().getId());

        new EditProjectPage().getMessageProjectCreated()
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldHave(Condition.partialText(
                        "Project \"" +
                                testData.getNewProjectDescription().getName() +
                                "\" has been successfully created."));

        new ProjectsPage()
                .open()
                .getSubprojects()
                .stream()
                .reduce((first, second) -> second)
                .get()
                .getHeader()
                .shouldHave(Condition.text(testData.getNewProjectDescription().getName()));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(testData.getNewProjectDescription().getName()))
                .body("parentProjectId", equalTo(testData.getNewProjectDescription().getParentProject().getLocator()))
                .body("buildTypes.count", equalTo(0));
    }

    @Test
    public void newProjectCanNotBeCreatedWhenProjectNameIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var createNewProjectPage = new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator());
        createNewProjectPage.createProjectManually("", testData.getNewProjectDescription().getId());
        createNewProjectPage.getCreateManuallyForm()
                .getErrorName()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Project name is empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newProjectCanNotBeCreatedWhenProjectIdIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var createNewProjectPage = new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator());
        createNewProjectPage.createProjectManually(testData.getNewProjectDescription().getName(), "");
        createNewProjectPage.getCreateManuallyForm()
                .getErrorProjectId()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Project ID must not be empty."));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
