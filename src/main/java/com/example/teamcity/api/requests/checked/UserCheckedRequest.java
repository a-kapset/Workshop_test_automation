package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UserUncheckedRequest;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class UserCheckedRequest extends Request implements CrudInterface<User> {

    public UserCheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public User create(User obj) {
        return new UserUncheckedRequest(spec)
                .create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(User.class);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(User obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UserUncheckedRequest(spec)
                .delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .asString();
    }
}
