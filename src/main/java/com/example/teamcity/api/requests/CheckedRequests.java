package com.example.teamcity.api.requests;

import com.example.teamcity.api.requests.checked.BuildConfigCheckedRequest;
import com.example.teamcity.api.requests.checked.ProjectCheckedRequest;
import com.example.teamcity.api.requests.checked.UserCheckedRequest;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private BuildConfigCheckedRequest buildConfigRequest;
    private ProjectCheckedRequest projectRequest;
    private UserCheckedRequest userRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.buildConfigRequest = new BuildConfigCheckedRequest(spec);
        this.projectRequest = new ProjectCheckedRequest(spec);
        this.userRequest = new UserCheckedRequest(spec);
    }
}
