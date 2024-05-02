package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public class SetProjectPermissionsRequest {

    public static void setProjectPermissions() {
        RestAssured
                .given()
                .spec(Specifications.getSpec().superUserSpec())
                .body("{\n" +
                        "    \"perProjectPermissions\": true,\n" +
                        "    \"modules\": {\n" +
                        "        \"module\": [\n" +
                        "            {\n" +
                        "                \"name\": \"HTTP-Basic\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}")
                .put("/app/rest/server/authSettings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}
