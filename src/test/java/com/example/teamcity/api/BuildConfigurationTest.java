package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.ProjectCheckedRequest;
import com.example.teamcity.api.requests.checked.UserCheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new UserCheckedRequest(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new ProjectCheckedRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getNewProjectDescription());

        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
    }
}
