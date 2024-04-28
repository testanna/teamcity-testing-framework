package com.example.teamcity.api.requests;

import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequest {
    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildConfig buildConfigRequest;

    public CheckedRequest(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.projectRequest = new CheckedProject(spec);
        this.buildConfigRequest = new CheckedBuildConfig(spec);
    }
}
