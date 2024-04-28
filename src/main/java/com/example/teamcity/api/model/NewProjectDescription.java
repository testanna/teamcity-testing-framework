package com.example.teamcity.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDescription {
    private Project parentProject;
    private String name;
    private String id;
    private Boolean copyAllAssociatedSettings;
}
