package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class UserUncheckedRequest extends Request implements CrudInterface<User> {
    private final static String USER_ENDPOINT = "/app/rest/users";

    public UserUncheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(User obj) {
        var response = RestAssured
                .given()
                .spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);

        TestDataStorage.getStorage().addToDeletionTasks(response, () -> {
            if (this.get(obj.getUsername()).statusCode() == HttpStatus.SC_OK) {
                this.delete(obj.getUsername());
            };
        });

        return response;
    }

    @Override
    public Response get(String name) {
        return RestAssured
                .given()
                .spec(spec)
                .get(USER_ENDPOINT + "/name:" + name);
    }

    @Override
    public Object update(User obj) {
        return null;
    }

    @Override
    public Response delete(String username) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(USER_ENDPOINT + "/username:" + username);
    }
}
