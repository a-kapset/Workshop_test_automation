package com.example.teamcity.api.requests;

import com.example.teamcity.api.requests.unchecked.BuildConfigUncheckedRequest;
import com.example.teamcity.api.requests.unchecked.ProjectUncheckedRequest;
import com.example.teamcity.api.requests.unchecked.UserUncheckedRequest;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private BuildConfigUncheckedRequest buildConfigRequest;
    private ProjectUncheckedRequest projectRequest;
    private UserUncheckedRequest userRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.buildConfigRequest = new BuildConfigUncheckedRequest(spec);
        this.projectRequest = new ProjectUncheckedRequest(spec);
        this.userRequest = new UserUncheckedRequest(spec);
    }
}
