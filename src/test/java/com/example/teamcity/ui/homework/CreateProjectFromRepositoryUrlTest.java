package com.example.teamcity.ui.homework;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

public class CreateProjectFromRepositoryUrlTest extends BaseUiTest {

    private static final String TEST_VCS_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test
    public void newProjectCanBeCreated() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator())
                .createProjectByUrl(TEST_VCS_URL)
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

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(testData.getNewProjectDescription().getName()))
                .body("parentProjectId", equalTo(testData.getNewProjectDescription().getParentProject().getLocator()))
                .body("buildTypes.buildType[0].name", equalTo(testData.getBuildType().getName()));
    }

    @Test
    public void newProjectCanNotBeCreatedWhenUrlIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator())
                .createProjectByUrl("")
                .getCreateFromUrlForm()
                .getErrorUrlMessage()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("URL must not be empty"));
    }

    @Test
    public void newProjectCanNotBeCreatedWhenProjectNameIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var createNewProjectPage = new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator());

        createNewProjectPage.createProjectByUrl(TEST_VCS_URL);
        createNewProjectPage.setupUrlProjectAndSubmit("", testData.getBuildType().getName());
        createNewProjectPage.getCreateProjectForm()
                .getErrorProjectName()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Project name must not be empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newProjectCanNotBeCreatedWhenBuildConfigNameIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var createNewProjectPage = new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator());

        createNewProjectPage.createProjectByUrl(TEST_VCS_URL);
        createNewProjectPage.setupUrlProjectAndSubmit(testData.getNewProjectDescription().getName(), "");
        createNewProjectPage.getCreateProjectForm()
                .getErrorBuildTypeName()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Build configuration name must not be empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newProjectIsNotCreatedWhenUserClicksCancel() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var createNewProjectPage = new CreateNewProjectPage()
                .open(testData.getNewProjectDescription().getParentProject().getLocator());

        createNewProjectPage.createProjectByUrl(TEST_VCS_URL);
        createNewProjectPage.setupUrlProject(
                        testData.getNewProjectDescription().getName(),
                        testData.getBuildType().getName());
        createNewProjectPage.cancel()
                .getCreateFromUrlForm()
                .getUrlInput()
                .shouldBe(Condition.visible, Duration.ofSeconds(3));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .get(testData.getNewProjectDescription().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}