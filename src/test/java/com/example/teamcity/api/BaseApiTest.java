package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {
    protected TestDataStorage testDataStorage;
    protected CheckedRequests chRequestsWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    protected UncheckedRequests unchRequestsWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());
    protected CheckedRequests chRequestsWithUnauthUser = new CheckedRequests(Specifications.getSpec().unauthSpec());
    protected UncheckedRequests unchRequestsWithUnauthUser = new UncheckedRequests(Specifications.getSpec().unauthSpec());

    @BeforeMethod
    protected void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    protected void cleanTest() {
        testDataStorage.delete();
    }
}
