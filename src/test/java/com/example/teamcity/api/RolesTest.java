package com.example.teamcity.api;

import com.example.teamcity.api.enums.Role;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest{

    @Test
    public void unauthorizedUserShouldNotHavePermissionsToCreateProject() {
        var testData = testDataStorage.addTestData();

        // check that unauthorized user can't create a project
        unchRequestsWithUnauthUser.getProjectRequest()
                .create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        // check that project is not created
        unchRequestsWithSuperUser.getProjectRequest()
                .get(testData.getNewProjectDescription().getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Responding with error, status code: 404 (Not Found)"));
    }

    @Test
    public void systemAdminShouldHavePermissionsToCreateProject() {
        var testData = testDataStorage.addTestData();
        var userRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.SYSTEM_ADMIN, "g"));
        chRequestsWithSuperUser.getUserRequest().create(testData.getUser());

        var project = userRequests.getProjectRequest().create(testData.getNewProjectDescription());
        var buildConfig = userRequests.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldHavePermissionsToCreateProject() {
        var testData = testDataStorage.addTestData();
        var userRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));

        // 1. create a project with "super user" permissions
        var project = chRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription());

        // 2. create a project admin with "super user" permissions
        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + testData.getNewProjectDescription().getId()));
        chRequestsWithSuperUser.getUserRequest().create(testData.getUser());

        // 3. create a build config with "project admin" permissions
        var buildConfig = userRequests.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHavePermissionsToCreateBuildConfigForAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(
                Role.PROJECT_ADMIN, "p:" + firstTestData.getNewProjectDescription().getId()));
        secondTestData.getUser().setRoles(TestDataGenerator.generateRoles(
                Role.PROJECT_ADMIN, "p:" + secondTestData.getNewProjectDescription().getId()));

        chRequestsWithSuperUser.getProjectRequest().create(firstTestData.getNewProjectDescription());
        chRequestsWithSuperUser.getProjectRequest().create(secondTestData.getNewProjectDescription());
        chRequestsWithSuperUser.getUserRequest().create(firstTestData.getUser());
        chRequestsWithSuperUser.getUserRequest().create(secondTestData.getUser());

        // try to create a build config as first project admin for second project (it's forbidden)
        new UncheckedRequests(Specifications.getSpec().authSpec(firstTestData.getUser()))
                .getBuildConfigRequest()
                .create(secondTestData.getBuildType())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("You do not have enough permissions to edit project with id: " + secondTestData.getNewProjectDescription().getId()));
    }
}
