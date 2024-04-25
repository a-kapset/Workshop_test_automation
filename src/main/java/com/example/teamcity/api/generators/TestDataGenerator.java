package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;

public class TestDataGenerator {

    public TestData generate() {
        var user = User.builder() // builder comes from lombok annotation
                .username("admin")
                .password("admin")
                .build();

        var newProjectDescription = NewProjectDescription.builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name(RandomData.getStringWithPrefix("name"))
                .id(RandomData.getStringWithPrefix("id"))
                .copyAllAssociatedSettings(true)
                .build();

        return TestData.builder()
                .user(user)
                .newProjectDescription(newProjectDescription)
                .build();
    }
}
