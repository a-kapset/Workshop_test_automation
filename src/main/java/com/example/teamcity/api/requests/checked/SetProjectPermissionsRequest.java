package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.models.Module;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

import java.util.List;

public class SetProjectPermissionsRequest {

    public static void setProjectPermissions() {
        RestAssured
                .given()
                .spec(Specifications.getSpec().superUserSpec())
                .body(AuthSettings.builder()
                        .perProjectPermissions(true)
                        .modules(Modules.builder()
                                .module(List.of(Module.builder()
                                        .name("HTTP-Basic")
                                        .build()))
                                .build())
                        .build())
                .put("/app/rest/server/authSettings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}
