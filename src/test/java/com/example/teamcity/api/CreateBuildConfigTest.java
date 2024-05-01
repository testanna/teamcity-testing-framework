package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.spec.Specification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateBuildConfigTest extends BaseApiTest {
    private TestData testData;

    @BeforeMethod
    public void generateAndCreateTestData() {
        testData = testDataStorage.addTestData();
        checkedWithSuperuser.getUserRequest().create(testData.getUser());
        checkedWithSuperuser.getProjectRequest().create(testData.getProject());
    }

    @Test
    public void createBuildConfigWithCorrectId() {
        var createdBuildConfig = new CheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softy.assertThat(createdBuildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithTheSameId() {
        var firstTestData = testData;
        var secondTestData = testDataStorage.addTestData();

        var firstBuildConfig = firstTestData.getBuildType();
        var secondBuildConfig = secondTestData.getBuildType();
        secondBuildConfig.setId(firstBuildConfig.getId());

        new CheckedProject(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(secondTestData.getProject());

        new CheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(firstBuildConfig);

        new UncheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(secondBuildConfig)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(
                        "The build configuration / template ID \"" + secondBuildConfig.getId() +
                                "\" is already used by another configuration or template"));
    }

    @Test
    public void buildConfigShouldBeCreatedWithTheSameNameInDifferentProjects() {
        var firstTestData = testData;
        var secondTestData = testDataStorage.addTestData();

        var firstBuildConfig = firstTestData.getBuildType();
        var secondBuildConfig = secondTestData.getBuildType();
        secondBuildConfig.setName(firstBuildConfig.getName());

        new CheckedProject(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(secondTestData.getProject());

        new CheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(firstBuildConfig);

        var createdBuildConfig = new CheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(secondBuildConfig);

        softy.assertThat(createdBuildConfig.getId()).isEqualTo(secondBuildConfig.getId());
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithTheSameNameInTheSameProject() {
        var firstTestData = testData;
        var secondTestData = testDataStorage.addTestData();

        var firstBuildConfig = firstTestData.getBuildType();
        var secondBuildConfig = secondTestData.getBuildType();
        secondBuildConfig.setName(firstBuildConfig.getName());
        secondBuildConfig.setProject(firstTestData.getProject());

        new CheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(firstBuildConfig);

        new UncheckedBuildConfig(Specification.getSpec().authSpec(firstTestData.getUser()))
                .create(secondBuildConfig)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(
                        "Build configuration with name \"%s\" already exists in project: \"%s\"",
                        secondBuildConfig.getName(), firstTestData.getProject().getName())));

        uncheckedWithSuperuser.getBuildConfigRequest()
                .get(secondBuildConfig.getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString(
                        "No build type nor template is found by id '" + secondBuildConfig.getId() + "'"));
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithEmptyId() {
        testData.getBuildType().setId("");

        new UncheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Build configuration or template ID must not be empty"));
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithEmptyName() {
        testData.getBuildType().setName("");

        new UncheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("When creating a build type, non empty name should be provided"));

        uncheckedWithSuperuser.getBuildConfigRequest()
                .get(testData.getBuildType().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString(
                        "No build type nor template is found by id '" + testData.getBuildType().getId() + "'"));
    }

    @Test
    public void createBuildConfigWithLongName() {
        testData.getBuildType().setName(RandomData.getString(5000));

        var createdBuildConfig = new CheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softy.assertThat(createdBuildConfig.getName()).isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void createBuildConfigWithSpecialSymbolsInName() {
        testData.getBuildType().setName("!@#$%^&*()~:,.;'|/?" + RandomData.getString());

        var createdBuildConfig = new CheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());

        softy.assertThat(createdBuildConfig.getName()).isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithEmptyProject() {
        testData.getBuildType().setProject(null);

        new UncheckedBuildConfig(Specification.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Build type creation request should contain project node"));

        uncheckedWithSuperuser.getBuildConfigRequest()
                .get(testData.getBuildType().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString(
                        "No build type nor template is found by id '" + testData.getBuildType().getId() + "'"));
    }
}
