package com.example.teamcity.report;

import com.codeborne.selenide.Screenshots;
import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AllureTestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attachScreenshot() {
        return getScreenshot();
    }

    private byte[] getScreenshot() {
        File screenshot = Screenshots.getLastScreenshot();

        if (screenshot != null && screenshot.exists()) {
            try {
                return Files.readAllBytes(screenshot.toPath());
            } catch (IOException e) {
                System.err.println("Failed to read screenshot file");
                e.printStackTrace();
            }
        }
        System.err.println("Screenshot was not created.");
        return new byte[0];
    }
}
