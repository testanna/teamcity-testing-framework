package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.model.User;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

public class BaseUiTest extends BaseTest {
    @BeforeSuite
    public void setupUiTest() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        checkedWithSuperuser.getUserRequest().create(user);
        new LoginPage().open().login(user);
    }
}
