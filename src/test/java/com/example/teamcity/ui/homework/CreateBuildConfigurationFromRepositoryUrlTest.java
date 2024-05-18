package com.example.teamcity.ui.homework;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.admin.BuildStepsConfigurationPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigPage;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

public class CreateBuildConfigurationFromRepositoryUrlTest extends BaseUiTest {
    private static final String TEST_VCS_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test
    public void newBuildConfigurationCanBeCreated() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        new CreateBuildConfigPage()
                .open(project.getId())
                .createBuildByUrl(TEST_VCS_URL)
                .setupUrlBuildAndSubmit(testData.getBuildType().getName());

        new BuildStepsConfigurationPage().getMessageBuildConfigCreated()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.partialText(String.format("New build configuration \"%s\"", testData.getBuildType().getName())))
                .shouldHave(Condition.partialText("have been successfully created"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(testData.getBuildType().getName()))
                .body("projectName", equalTo(testData.getNewProjectDescription().getName()))
                .body("projectId", equalTo(testData.getNewProjectDescription().getId()));
    }

    @Test
    public void newBuildConfigurationCanNotBeCreatedWhenUrlIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        new CreateBuildConfigPage()
                .open(project.getId())
                .createBuildByUrl("")
                .getCreateFromUrlForm()
                .getErrorUrlMessage()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("URL must not be empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newBuildConfigurationCanNotBeCreatedWhenNameIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        var createBuildConfigPage = new CreateBuildConfigPage().open(project.getId());
        createBuildConfigPage.createBuildByUrl(TEST_VCS_URL);
        createBuildConfigPage.setupUrlBuildAndSubmit("");
        createBuildConfigPage.getCreateBuildForm()
                .getErrorBuildTypeName()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Build configuration name must not be empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newBuildConfigurationCanNotBeCreatedWhenUserClicksCancel() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        var createBuildConfigPage = new CreateBuildConfigPage().open(project.getId());
        createBuildConfigPage.createBuildByUrl(TEST_VCS_URL);
        createBuildConfigPage.setupUrlBuild(testData.getBuildType().getName());
        createBuildConfigPage.cancel()
                .getCreateFromUrlForm()
                .getUrlInput()
                .shouldBe(Condition.visible, Duration.ofSeconds(3));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
