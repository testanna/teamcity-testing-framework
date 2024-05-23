package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class SetUpTest extends BaseUiTest {
    @Test
    public void startUpTest() {
        new StartUpPage().open()
                .setUpTeamcityServer()
                .waitUntilCreateAccountHeaderVisible();
    }
}
