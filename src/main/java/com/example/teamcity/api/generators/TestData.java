package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription newProjectDescription;

    public void delete() {
        new ProjectUncheckedRequest(this.user).delete(newProjectDescription.getId());
    }
}
