package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.element;

public class StartUpPage extends Page {
    private final SelenideElement restoreButton = element(Selectors.byId("restoreButton"));
    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private final SelenideElement dbTitle = element(byText("Database connection setup"));
    private final SelenideElement licenseTitle = element(withText("License Agreement for JetBrains"));
    private final SelenideElement acceptLicenseCheckBox = element(Selectors.byId("accept"));
    private final SelenideElement continueButton = element(byName("Continue"));
    private final SelenideElement createAccountHeader = element(byText("Create Administrator Account"));

    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    public StartUpPage setUpTeamcityServer() {
        waitUntilPageIsLoaded();
        restoreButton.shouldBe(Condition.visible, Duration.ofMinutes(2));
        proceedButton.click();

        waitUntilPageIsLoaded();
        dbTitle.shouldBe(Condition.visible, Duration.ofMinutes(1));
        proceedButton.click();

        waitUntilPageIsLoaded();
        licenseTitle.shouldBe(Condition.visible, Duration.ofMinutes(5));
        acceptLicenseCheckBox.scrollTo();
        acceptLicenseCheckBox.click();
        continueButton.click();

        return this;
    }

    public void waitUntilCreateAccountHeaderVisible() {
        createAccountHeader.shouldBe(Condition.visible, Duration.ofMinutes(2));
    }


}
