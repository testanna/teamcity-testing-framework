package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateProjectTest extends BaseApiTest {
    private TestData testData;

    @BeforeMethod
    public void addTestDataAndCreateUser() {
        testData = testDataStorage.addTestData();
        checkedWithSuperuser.getUserRequest().create(testData.getUser());
    }

    @Test
    public void createProjectWithCorrectId() {
        var project = (Project) new CheckedBase(Specification.getSpec().authSpec(testData.getUser()), Project.class)
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectShouldNotBeCreatedWithTheSameId() {
        var firstTestData = testData;
        var secondTestData = testDataStorage.addTestData();

        var firstProject = firstTestData.getProject();
        var secondProject = secondTestData.getProject();
        secondProject.setId(firstProject.getId());

        new CheckedBase(Specification.getSpec().authSpec(firstTestData.getUser()), Project.class)
                .create(firstProject);

        new UncheckedBase(Specification.getSpec().authSpec(firstTestData.getUser()), Project.class)
                .create(secondProject)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(
                        "Project ID \"" + secondProject.getId() + "\" is already used by another project"));
    }

    @Test
    public void projectShouldNotBeCreatedWithTheSameName() {
        var firstTestData = testData;
        var secondTestData = testDataStorage.addTestData();

        var firstProject = firstTestData.getProject();
        var secondProject = secondTestData.getProject();
        secondProject.setName(firstProject.getName());

        new CheckedBase(Specification.getSpec().authSpec(firstTestData.getUser()), Project.class)
                .create(firstProject);

        new UncheckedBase(Specification.getSpec().authSpec(firstTestData.getUser()), Project.class)
                .create(secondProject)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(
                        "Project with this name already exists: " + secondProject.getName()));

        uncheckedWithSuperuser.getProjectRequest()
                .get(secondProject.getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator"
                        + " 'count:1,id:" + secondProject.getId() + "'"));
    }

    @Test
    public void projectShouldNotBeCreatedWithEmptyId() {
        testData.getProject().setId("");

        new UncheckedBase(Specification.getSpec().authSpec(testData.getUser()), Project.class)
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty"));
    }

    @Test
    public void projectShouldNotBeCreatedWithEmptyName() {
        testData.getProject().setName("");

        new UncheckedBase(Specification.getSpec().authSpec(testData.getUser()), Project.class)
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));

        uncheckedWithSuperuser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator"
                        + " 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    @Test
    public void projectShouldBeCreatedWithLongName() {
        testData.getProject().setName(RandomData.getString(5000));

        var project = (Project)new CheckedBase(Specification.getSpec().authSpec(testData.getUser()), Project.class)
                .create(testData.getProject());

        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
    }

    @Test
    public void projectShouldBeCreatedWithSpecialSymbolsInName() {
        testData.getProject().setName("!@#$%^&*()~:,.;'|/?" + RandomData.getString());

        var project = (Project)new CheckedBase(Specification.getSpec().authSpec(testData.getUser()), Project.class)
                .create(testData.getProject());

        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
    }
}
