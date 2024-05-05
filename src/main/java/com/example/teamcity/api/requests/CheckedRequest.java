package com.example.teamcity.api.requests;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.model.User;
import com.example.teamcity.api.requests.checked.CheckedBase;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequest {
    private final CheckedBase userRequest;
    private final CheckedBase projectRequest;
    private final CheckedBase buildConfigRequest;

    public CheckedRequest(RequestSpecification spec) {
        this.userRequest = new CheckedBase(spec, User.class);
        this.projectRequest = new CheckedBase(spec, Project.class);
        this.buildConfigRequest = new CheckedBase(spec, BuildType.class);
    }
}
