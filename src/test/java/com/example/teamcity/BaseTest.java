package com.example.teamcity;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected SoftAssertions softy;

    @BeforeMethod
    protected void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterMethod
    protected void afterTest() {
        softy.assertAll();
    }
}
