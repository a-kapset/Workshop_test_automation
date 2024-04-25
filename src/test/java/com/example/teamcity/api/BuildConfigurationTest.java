package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.ProjectCheckedRequest;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var project = new ProjectCheckedRequest(testData.getUser()).create(testData.getNewProjectDescription());
        softy.assertThat(project.getId()).isEqualTo(testData.getNewProjectDescription().getId());
    }
}
