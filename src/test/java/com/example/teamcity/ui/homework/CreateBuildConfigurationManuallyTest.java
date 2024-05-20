package com.example.teamcity.ui.homework;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigPage;
import com.example.teamcity.ui.pages.admin.VCSrootsConfigurationPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

public class CreateBuildConfigurationManuallyTest extends BaseUiTest {

    @Test
    public void buildConfigCanBeCreated() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        new CreateBuildConfigPage()
                .open(project.getId())
                .createBuildManually(testData.getBuildType().getName(), testData.getBuildType().getId());

        new VCSrootsConfigurationPage().getMessageBuildConfigCreated()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.partialText("Build configuration successfully created"));

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
    public void newBuildConfigCanNotBeCreatedWhenBuildConfigNameIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        var createBuildConfigPage = new CreateBuildConfigPage().open(project.getId());
        createBuildConfigPage.createBuildManually("", testData.getBuildType().getId());
        createBuildConfigPage.getCreateManuallyForm().getErrorBuildTypeName()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("Name must not be empty"));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void newBuildConfigCanNotBeCreatedWhenBuildConfigIdIsEmpty() {
        var testData = testDataStorage.addTestData();

        loginAsAuthorizedUser(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getNewProjectDescription());

        var createBuildConfigPage = new CreateBuildConfigPage().open(project.getId());
        createBuildConfigPage.createBuildManually(testData.getBuildType().getName(), "");
        createBuildConfigPage.getCreateManuallyForm().getErrorBuildTypeExternalId()
                .shouldBe(Condition.visible, Duration.ofSeconds(1))
                .shouldHave(Condition.text("The ID field must not be empty."));

        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .get(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
