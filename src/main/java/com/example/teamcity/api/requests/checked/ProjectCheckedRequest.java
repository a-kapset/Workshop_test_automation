package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class ProjectCheckedRequest extends Request implements CrudInterface<NewProjectDescription> {

    public ProjectCheckedRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Project create(NewProjectDescription obj) {
        return new ProjectUncheckedRequest(this.spec)
                .create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Project.class);
    }

    @Override
    public Project get(String id) {
        return new ProjectUncheckedRequest(spec)
                .get(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(NewProjectDescription obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new ProjectUncheckedRequest(this.spec)
                .delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();
    }
}
