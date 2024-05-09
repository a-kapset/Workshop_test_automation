package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class BuildConfigUncheckedRequest extends Request implements CrudInterface<BuildType> {
    private static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";

    public BuildConfigUncheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(BuildType obj) {
        var response = RestAssured
                .given()
                .spec(spec)
                .body(obj)
                .post(BUILD_CONFIG_ENDPOINT);

        TestDataStorage.getStorage().addToDeletionTasks(response, () -> {
            if (this.get(obj.getId()).statusCode() == HttpStatus.SC_OK) {
                this.delete(obj.getId());
            };
        });

        return response;
    }

    @Override
    public Response get(String name) {
        return RestAssured
                .given()
                .spec(spec)
                .get(BUILD_CONFIG_ENDPOINT + "/name:" + name);
    }

    @Override
    public Object update(BuildType obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(BUILD_CONFIG_ENDPOINT + "/id:" + id);
    }
}
