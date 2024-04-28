package com.example.teamcity.api.generators;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.NewProjectDescription;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.model.Role;
import com.example.teamcity.api.model.Roles;
import com.example.teamcity.api.model.User;

import java.util.Collections;

public class TestDataGenerator {
    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(Collections.singletonList(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build())
                .build();

        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .id(RandomData.getString())
                .name(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();

        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(project)
                .build();

        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(com.example.teamcity.api.enums.Role role, String scope) {
        return Roles.builder()
                .role(Collections.singletonList(Role.builder()
                        .roleId(role.getText())
                        .scope(scope)
                        .build()))
                .build();
    }
}
