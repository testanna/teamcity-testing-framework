package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.InputElement;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.element;

public class CreateNewProject extends Page {
    private final InputElement urlInput = new InputElement(element(Selectors.byId("url")));
    private final InputElement projectNameInput = new InputElement(element(Selectors.byId("projectName")));
    private final InputElement buildTypeNameInput = new InputElement(element(Selectors.byId("buildTypeName")));
    private final InputElement branchInput = new InputElement(element(Selectors.byId("branch")));
    private final SelenideElement useSelectedButton = element(byText("Use selected"));

    public CreateNewProject open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId
                + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.waitUntilInputIsVisible();
        urlInput.enterText(url);
        submit();

        return this;
    }

    public CreateNewProject enterNames(String projectName, String buildTypeName) {
        waitUntilProjectIsSaved();

        projectNameInput.enterText(projectName);
        buildTypeNameInput.enterText(buildTypeName);

        return this;
    }

    public CreateNewProject setupProject(String projectName, String buildTypeName) {
        enterNames(projectName, buildTypeName);
        submit();

        useSelectedButton.shouldBe(Condition.enabled, Duration.ofSeconds(15));

        return this;
    }

    public CreateNewProject enterBranch(String branch) {
        branchInput.enterText(branch);

        return this;
    }

    public CreateNewProject clickProceed() {
        submit();
        return this;
    }

    public String getUrlError() {
        return urlInput.getErrorText();
    }

    public String getDefaultBranchError() {
        return branchInput.getErrorText();
    }

    private void waitUntilProjectIsSaved() {
        savingWaitingMarker.shouldBe(Condition.attribute("style", "display: none;"),
                Duration.ofSeconds(30));
        projectNameInput.waitUntilInputIsVisible();
    }
}
