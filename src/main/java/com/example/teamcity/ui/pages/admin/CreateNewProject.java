package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewProject extends Page {
    private final SelenideElement urlInput = element(Selectors.byId("url"));
    private final SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private final SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));

    public CreateNewProject open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId
                + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        waitUntilProjectIsSaved();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);

        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);

        submit();
    }

    private void waitUntilProjectIsSaved() {
        savingWaitingMarker.shouldBe(Condition.attribute("style", "display: none;"),
                Duration.ofSeconds(30));
        projectNameInput.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
