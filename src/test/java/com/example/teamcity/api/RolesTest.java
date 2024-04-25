package com.example.teamcity.api;

import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.requests.checked.ProjectCheckedRequest;
import com.example.teamcity.api.requests.checked.UserCheckedRequest;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

public class RolesTest extends BaseApiTest{

    @Test
    public void unauthorizedUser() {
        // check that unauthorized user can't create a project
        new ProjectUncheckedRequest(Specifications.getSpec().unauthSpec())
                .create(testData.getNewProjectDescription())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        // check that project is not created
        new ProjectUncheckedRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .get(testData.getNewProjectDescription().getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Responding with error, status code: 404 (Not Found)"));
    }

    @Test
    public void systemAdminTest() {
        testData.getUser().setRoles(Roles.builder()
                .role(Arrays.asList(Role.builder()
                        .roleId("SYSTEM_ADMIN")
                        .scope("g")
                        .build()))
                .build());

        new UserCheckedRequest(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new ProjectCheckedRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getNewProjectDescription());
        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
    }
}
