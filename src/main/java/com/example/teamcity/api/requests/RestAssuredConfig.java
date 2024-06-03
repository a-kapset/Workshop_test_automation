package com.example.teamcity.api.requests;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.RestAssured;

public class RestAssuredConfig {
    public static void setup() {
        RestAssured.filters(new SwaggerCoverageRestAssured());
    }
}
