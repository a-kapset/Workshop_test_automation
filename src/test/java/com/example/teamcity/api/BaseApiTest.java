package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.requests.RestAssuredConfig;
import org.testng.annotations.BeforeClass;

public class BaseApiTest extends BaseTest {
    @BeforeClass
    public void setUp() {
        RestAssuredConfig.setup();
    }
}
