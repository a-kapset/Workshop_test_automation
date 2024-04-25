package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.generators.TestDataGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {
    protected TestData testData;

    @BeforeMethod
    protected void setupTest() {
        testData = new TestDataGenerator().generate();
    }

    @AfterMethod
    protected void cleanTest() {
        testData.delete();
    }
}
