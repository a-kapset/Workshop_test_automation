package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import com.example.teamcity.api.requests.unchecked.UserUncheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription newProjectDescription;
    private BuildType buildType;

    public void delete() {
        var spec = Specifications.getSpec().authSpec(user); // TODO: consider to use super admin spec here
        new ProjectUncheckedRequest(spec).delete(newProjectDescription.getId());
        new UserUncheckedRequest(spec).delete(user.getUsername());
    }
}
