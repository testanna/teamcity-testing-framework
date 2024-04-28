package com.example.teamcity.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String  parentProjectId;
    private String name;
    private String id;
    private String locator;
}
