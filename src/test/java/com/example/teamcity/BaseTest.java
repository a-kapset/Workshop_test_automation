package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.checked.SetProjectPermissionsRequest;
import com.example.teamcity.api.spec.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected SoftAssertions softy;
    protected TestDataStorage testDataStorage;
    protected CheckedRequests chRequestsWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    protected UncheckedRequests unchRequestsWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());
    protected CheckedRequests chRequestsWithUnauthUser = new CheckedRequests(Specifications.getSpec().unauthSpec());
    protected UncheckedRequests unchRequestsWithUnauthUser = new UncheckedRequests(Specifications.getSpec().unauthSpec());

    @BeforeSuite
    protected void setPermissions() {
        SetProjectPermissionsRequest.setProjectPermissions();
    }

    @BeforeMethod
    protected void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    protected void afterTest() {
        testDataStorage.delete();
        softy.assertAll();
    }
}
