package com.example.teamcity.api.generators;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.NewProjectDescription;
import com.example.teamcity.api.model.User;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import com.example.teamcity.api.spec.Specification;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        var spec = Specification.getSpec().authSpec(user);
        new UncheckedProject(spec).delete(project.getId());
        new UncheckedUser(spec).delete(user.getUsername());
    }
}
