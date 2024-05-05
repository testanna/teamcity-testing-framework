package com.example.teamcity.api.requests;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.model.User;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequest {
    private final UncheckedBase userRequest;
    private final UncheckedBase projectRequest;
    private final UncheckedBase buildConfigRequest;

    public UncheckedRequest(RequestSpecification spec) {
        this.userRequest = new UncheckedBase(spec, User.class);
        this.projectRequest = new UncheckedBase(spec, Project.class);
        this.buildConfigRequest = new UncheckedBase(spec, BuildType.class);
    }
}
