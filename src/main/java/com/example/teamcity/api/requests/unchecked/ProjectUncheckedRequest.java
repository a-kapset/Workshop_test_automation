package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class ProjectUncheckedRequest extends Request implements CrudInterface<NewProjectDescription> {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    public ProjectUncheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(NewProjectDescription obj) {
        var response =  RestAssured
                .given()
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);

        TestDataStorage.getStorage().addToDeletionTasks(response, () -> {
            if (this.get(obj.getId()).statusCode() == HttpStatus.SC_OK) {
                this.delete(obj.getId());
            };
        });

        return response;
    }

    @Override
    public Response get(String id) {
        return RestAssured
                .given()
                .spec(spec)
                .get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(NewProjectDescription obj) {
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
