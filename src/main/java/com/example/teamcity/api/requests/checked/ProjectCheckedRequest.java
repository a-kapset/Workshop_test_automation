package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import org.apache.http.HttpStatus;

public class ProjectCheckedRequest implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private final User user;

    public ProjectCheckedRequest(User user) {
        this.user = user;
    }

    @Override
    public Project create(Object obj) {
        return new ProjectUncheckedRequest(this.user)
                .create(obj)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Project.class);
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
    public String delete(String id) {
        return new ProjectUncheckedRequest(this.user)
                .delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();
    }
}
