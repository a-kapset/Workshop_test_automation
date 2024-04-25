package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProjectDescription {
    private Project parentProject;
    private String name;
    private String id;
    private boolean copyAllAssociatedSettings;
}