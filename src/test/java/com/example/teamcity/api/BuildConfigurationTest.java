package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.ProjectCheckedRequest;
import com.example.teamcity.api.requests.checked.UserCheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})
public class BuildConfigurationTest extends BaseApiTest {

    @Test
    @Description("API: Build Configuration Test")
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new UserCheckedRequest(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new ProjectCheckedRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getNewProjectDescription());

        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
    }
}
