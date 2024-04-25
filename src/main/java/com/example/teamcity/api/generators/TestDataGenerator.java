package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.*;

import java.util.Arrays;

public class TestDataGenerator {

    public TestData generate() {
        var user = User.builder() // builder comes from lombok annotation
                .username(RandomData.getStringWithPrefix("uname"))
                .password(RandomData.getStringWithPrefix("upass"))
                .email(RandomData.getString() + "@test.net")
                .roles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build())
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
