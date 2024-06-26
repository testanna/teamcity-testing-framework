package com.example.teamcity.api;

import com.example.teamcity.api.enums.Role;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest {
    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedBase(Specification.getSpec().unauthSpec(), Project.class)
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(Matchers.containsString("Authentication required"));

        uncheckedWithSuperuser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator"
                        + " 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    @Test
    public void systemAdminShouldHaveRightToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.SYSTEM_ADMIN, "g"));

        uncheckedWithSuperuser.getUserRequest()
                .create(testData.getUser());

        var project = (Project) new CheckedBase(Specification.getSpec()
                .authSpec(testData.getUser()), Project.class)
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperuser.getProjectRequest()
                .create(testData.getProject());

        testData.getUser().setRoles(
                TestDataGenerator
                        .generateRoles(Role.PROJECT_ADMIN, "p:" + testData.getProject().getId()));

        checkedWithSuperuser.getUserRequest()
                .create(testData.getUser());

        var buildConfig = (BuildType) new CheckedBase(Specification.getSpec().authSpec(testData.getUser()),
                BuildType.class)
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightToCreateBuildConfigToAnotherProject() {
        var firstTestData = TestDataGenerator.generate();
        var secondTestData = TestDataGenerator.generate();

        checkedWithSuperuser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperuser.getProjectRequest().create(secondTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator.
                generateRoles(Role.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));

        checkedWithSuperuser.getUserRequest().create(firstTestData.getUser());

        secondTestData.getUser().setRoles(TestDataGenerator.
                generateRoles(Role.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));

        checkedWithSuperuser.getUserRequest()
                .create(secondTestData.getUser());

        new UncheckedBase(Specification.getSpec().authSpec(secondTestData.getUser()), BuildType.class)
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString(
                        "You do not have enough permissions to edit project with id: "
                                + firstTestData.getProject().getId()));
    }
}
