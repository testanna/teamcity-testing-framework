package com.example.teamcity.api.generators;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.NewProjectDescription;
import com.example.teamcity.api.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;
}
