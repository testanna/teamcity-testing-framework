package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequest;
import com.example.teamcity.api.requests.UncheckedRequest;
import com.example.teamcity.api.spec.Specification;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected SoftAssertions softy;

    public TestDataStorage testDataStorage;
    public CheckedRequest checkedWithSuperuser = new CheckedRequest(Specification.getSpec().superUserSpec());
    public UncheckedRequest uncheckedWithSuperuser = new UncheckedRequest(Specification.getSpec().superUserSpec());

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        testDataStorage.deleteCreatedEntities();
        softy.assertAll();
    }
}
