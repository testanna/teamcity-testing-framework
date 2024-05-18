package com.example.teamcity.ui.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import java.time.Duration;

public class InputElement extends ProjectElement {
    private final SelenideElement error;

    public InputElement(SelenideElement element) {
        super(element);
        this.error = element.parent().parent().find(Selectors.byClass("error"));
    }

    public void enterText(String text) {
        if (text != null) {
            element.clear();
            element.sendKeys(text);
        }
    }

    public String getErrorText() {
        error.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return error.text();
    }

    public void waitUntilInputIsVisible() {
        element.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
