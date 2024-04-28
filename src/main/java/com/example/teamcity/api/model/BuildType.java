package com.example.teamcity.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildType {
    private String id;
    private String name;
    private NewProjectDescription project;
}
