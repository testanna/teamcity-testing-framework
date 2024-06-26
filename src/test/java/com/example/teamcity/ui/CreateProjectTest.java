package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class CreateProjectTest extends BaseUiTest {
    private TestData testData;

    @Test
    public void authorizedUserShouldBeAbleCreateProject() {
        testData = testDataStorage.addTestData();
        var url = "https://github.com/testanna/Scoring";

        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));

        var apiProject = (Project) checkedWithSuperuser.getProjectRequest()
                .getByName(testData.getProject().getName());

        softy.assertThat(apiProject.getParentProjectId()).isEqualTo("_Root");
    }

    @Test
    public void projectShouldNotBeCreatedWithEmptyUrl() {
        testData = testDataStorage.addTestData();
        var url = "";

        loginAsUser(testData.getUser());

        var errorMessage = new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .getUrlError();

        softy.assertThat(errorMessage).isEqualTo("URL must not be empty");

        uncheckedWithSuperuser.getProjectRequest()
                .getByName(testData.getProject().getName())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void projectShouldNotBeCreatedWithInvalidUrl() {
        testData = testDataStorage.addTestData();
        var url = RandomData.getString();

        loginAsUser(testData.getUser());

        var errorMessage = new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .getUrlError();

        softy.assertThat(errorMessage)
                .isEqualTo("Cannot create a project using the specified URL. The URL is not recognized.");

        uncheckedWithSuperuser.getProjectRequest()
                .getByName(testData.getProject().getName())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
