package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProjectUncheckedRequest extends Request implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    public ProjectUncheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return RestAssured
                .given()
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String name) {
        return RestAssured
                .given()
                .spec(spec)
                .get(PROJECT_ENDPOINT + "/name:" + name);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
