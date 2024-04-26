package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserUncheckedRequest extends Request implements CrudInterface {
    private final static String USER_ENDPOINT = "/app/rest/users";

    public UserUncheckedRequest(RequestSpecification spec) {
        super(spec);
    }


    @Override
    public Response create(Object obj) {
        return RestAssured
                .given()
                .spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);
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
                .spec(spec)
                .delete(USER_ENDPOINT + "/username:" + id);
    }
}
