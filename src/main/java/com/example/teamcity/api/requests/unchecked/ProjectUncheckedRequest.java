package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProjectUncheckedRequest implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private User user;

    public ProjectUncheckedRequest(User user) {
        this.user = user;
    }

    @Override
    public Response create(Object obj) {
        return RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(this.user))
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(this.user))
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
