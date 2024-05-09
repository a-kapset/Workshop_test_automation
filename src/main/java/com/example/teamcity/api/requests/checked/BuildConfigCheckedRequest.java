package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.BuildConfigUncheckedRequest;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class BuildConfigCheckedRequest extends Request implements CrudInterface<BuildType> {

    public BuildConfigCheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public BuildType create(BuildType obj) {
        return new BuildConfigUncheckedRequest(spec)
                .create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(BuildType.class);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(BuildType obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new BuildConfigUncheckedRequest(spec)
                .delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .asString();
    }
}
