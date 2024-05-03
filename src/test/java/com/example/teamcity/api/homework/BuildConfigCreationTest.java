package com.example.teamcity.api.homework;

import com.example.teamcity.api.BaseApiTest;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class BuildConfigCreationTest extends BaseApiTest {

    @Test
    public void buildConfigurationCanBeCreatedWithCorrectData() {
        var testData = testDataStorage.addTestData();

        chRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription());
        var buildConfig = chRequestsWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void severalBuildConfigurationsCanBeCreatedForTheSameProject() {
        var testData1 = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        var project = testData1.getNewProjectDescription();
        testData2.getBuildType().setProject(project);

        chRequestsWithSuperUser.getProjectRequest().create(project);

        var buildConfig1 = chRequestsWithSuperUser.getBuildConfigRequest().create(testData1.getBuildType());
        var buildConfig2 = chRequestsWithSuperUser.getBuildConfigRequest().create(testData2.getBuildType());

        softy.assertThat(buildConfig1.getId()).isEqualTo(testData1.getBuildType().getId());
        softy.assertThat(buildConfig2.getId()).isEqualTo(testData2.getBuildType().getId());
        softy.assertThat(buildConfig1.getProject().getName()).isEqualTo(project.getName());
        softy.assertThat(buildConfig2.getProject().getName()).isEqualTo(project.getName());
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithExistingNameForTheSameProject() {
        var testData1 = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        var project = testData1.getNewProjectDescription();
        var buildConfigName = testData1.getBuildType().getName();
        testData2.getBuildType().setProject(project);
        testData2.getBuildType().setName(buildConfigName);

        chRequestsWithSuperUser.getProjectRequest().create(project);
        chRequestsWithSuperUser.getBuildConfigRequest().create(testData1.getBuildType());
        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData2.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Build configuration with name \""
                        + buildConfigName
                        + "\" already exists in project: \""
                        + project.getName()
                        + "\""));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithoutName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName("");

        chRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription());
        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("When creating a build type, non empty name should be provided"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithExistingIDForTheSameProject() {
        var testData1 = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        var project = testData1.getNewProjectDescription();
        var buildConfigId = testData1.getBuildType().getId();
        testData2.getBuildType().setProject(project);
        testData2.getBuildType().setId(buildConfigId);

        chRequestsWithSuperUser.getProjectRequest().create(project);
        chRequestsWithSuperUser.getBuildConfigRequest().create(testData1.getBuildType());
        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData2.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \""
                        + buildConfigId
                        + "\" is already used by another configuration or template"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithoutID() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setId("");

        chRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription());
        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Build configuration or template ID must not be empty"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithNonExistingProject() {
        var testData = testDataStorage.addTestData();

        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '" +
                        testData.getBuildType().getProject().getId() + "'"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedCanNotBeCreatedWithoutProject() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setProject(null);

        unchRequestsWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Build type creation request should contain project node"));
    }
}
