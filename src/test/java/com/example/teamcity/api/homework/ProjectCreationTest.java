package com.example.teamcity.api.homework;

import com.example.teamcity.api.BaseApiTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.Project;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class ProjectCreationTest extends BaseApiTest {

    @Test
    public void projectCanBeCreatedWithRootParentProject() {
        var testData = testDataStorage.addTestData();
        var project = chRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription());

        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
    }

    @Test
    public void projectCanBeCreatedWithNonRootParentProject() {
        var testDataParent = testDataStorage.addTestData();
        var testDataChild = testDataStorage.addTestData();
        var parentProject = chRequestsWithSuperUser.getProjectRequest().create(testDataParent.getNewProjectDescription());

        // update parent project value
        testDataChild.getNewProjectDescription().setParentProject(
                Project.builder()
                        .locator(parentProject.getName())
                        .build()
        );

        var childProject = chRequestsWithSuperUser.getProjectRequest().create(testDataChild.getNewProjectDescription());

        softy.assertThat(childProject.getId()).isEqualTo(testDataChild.getNewProjectDescription().getId());
        softy.assertThat(childProject.getParentProjectId()).isEqualTo(testDataParent.getNewProjectDescription().getId());
    }

    @Test
    public void projectCanBeCreatedWithoutCopyingAllAssociatedSettings() {
        var testDataParent = testDataStorage.addTestData();
        var testDataChild = testDataStorage.addTestData();
        var parentProject = chRequestsWithSuperUser.getProjectRequest().create(testDataParent.getNewProjectDescription());

        // update parent project value
        testDataChild.getNewProjectDescription().setParentProject(
                Project.builder()
                        .locator(parentProject.getName())
                        .build()
        );

        // update copy all associated settings value
        testDataChild.getNewProjectDescription().setCopyAllAssociatedSettings(false);

        var childProject = chRequestsWithSuperUser.getProjectRequest().create(testDataChild.getNewProjectDescription());

        softy.assertThat(childProject.getId()).isEqualTo(testDataChild.getNewProjectDescription().getId());
        softy.assertThat(childProject.getParentProjectId()).isEqualTo(testDataParent.getNewProjectDescription().getId());
    }

    @Test
    public void projectCanNotBeCreatedWithExistingName() {
        var testData1 = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        testData2.getNewProjectDescription().setName(testData1.getNewProjectDescription().getName());

        chRequestsWithSuperUser.getProjectRequest().create(testData1.getNewProjectDescription());
        unchRequestsWithSuperUser.getProjectRequest().create(testData2.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(
                        String.format("Project with this name already exists: %s", testData2.getNewProjectDescription().getName())
                ));
    }

    @Test
    public void projectCanNotBeCreatedWithEmptyName() {
        var testData = testDataStorage.addTestData();
        testData.getNewProjectDescription().setName("");

        unchRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));
    }

    @Test
    public void projectCanNotBeCreatedWithExistingId() {
        var testData1 = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        testData2.getNewProjectDescription().setId(testData1.getNewProjectDescription().getId());

        chRequestsWithSuperUser.getProjectRequest().create(testData1.getNewProjectDescription());
        unchRequestsWithSuperUser.getProjectRequest().create(testData2.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(
                        String.format("Project ID \"%s\" is already used by another project", testData1.getNewProjectDescription().getId())
                ));
    }

    @Test
    public void projectCanNotBeCreatedWithEmptyId() {
        var testData = testDataStorage.addTestData();
        testData.getNewProjectDescription().setId("");

        unchRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty"));
    }

    @Test
    public void projectCanNotBeCreatedWithoutParentProject() {
        var testData = testDataStorage.addTestData();
        testData.getNewProjectDescription().setParentProject(
                Project.builder()
                        .locator("")
                        .build()
        );

        unchRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("No project specified"));
    }

    @Test
    public void projectCanNotBeCreatedWithNonExistingParentProject() {
        var testData = testDataStorage.addTestData();
        var nonExistingName = RandomData.getString();
        testData.getNewProjectDescription().setParentProject(
                Project.builder()
                        .locator(nonExistingName)
                        .build()
        );

        unchRequestsWithSuperUser.getProjectRequest().create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString(
                        String.format("No project found by name or internal/external id '%s'", nonExistingName)
                ));
    }
}
