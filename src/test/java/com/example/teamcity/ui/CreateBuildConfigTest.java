package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.model.Project;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class CreateBuildConfigTest extends BaseUiTest {
    private TestData testData;
    private static final String url = "https://github.com/testanna/Scoring";

    @Test
    public void authorizedUserShouldBeAbleCreateProjectAndBuildConfig() {
        testData = testDataStorage.addTestData();

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
        var apiBuildConfig = (BuildType) checkedWithSuperuser.getBuildConfigRequest()
                .getByName(testData.getBuildType().getName());

        softy.assertThat(apiBuildConfig.getProjectName()).isEqualTo(apiProject.getName());
        softy.assertThat(apiBuildConfig.getProjectId()).isEqualTo(apiProject.getId());
    }

    @Test
    public void buildConfigShouldNotBeCreatedWithEmptyBranch() {
        testData = testDataStorage.addTestData();
        var defaultBranch = "";

        loginAsUser(testData.getUser());

        var errorMessage = new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .enterNames(testData.getProject().getName(), testData.getBuildType().getName())
                .enterBranch(defaultBranch)
                .clickProceed()
                .getDefaultBranchError();

        softy.assertThat(errorMessage)
                .isEqualTo("Branch name must be specified");

        uncheckedWithSuperuser.getProjectRequest()
                .getByName(testData.getProject().getName())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

        uncheckedWithSuperuser.getBuildConfigRequest()
                .getByName(testData.getBuildType().getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
