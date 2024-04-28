package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequest;
import com.example.teamcity.api.requests.UncheckedRequest;
import com.example.teamcity.api.spec.Specification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;
    public CheckedRequest checkedWithSuperuser = new CheckedRequest(Specification.getSpec().superUserSpec());
    public UncheckedRequest uncheckedWithSuperuser = new UncheckedRequest(Specification.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }
}
