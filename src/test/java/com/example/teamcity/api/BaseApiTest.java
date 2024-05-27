package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.requests.unchecked.UncheckedAuthSettings;
import org.testng.annotations.BeforeTest;

public class BaseApiTest extends BaseTest {
    @BeforeTest
    public void setAuthSettings() {
        new UncheckedAuthSettings().setDefaultAuthSettings();
    }
}
