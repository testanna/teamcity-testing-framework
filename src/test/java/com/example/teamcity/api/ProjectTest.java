package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.spec.Specification;
import org.testng.annotations.Test;

public class ProjectTest extends BaseApiTest {
    @Test
    public void createsProjectWithCorrectId() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperuser.getUserRequest().create(testData.getUser());
        var project = new CheckedProject(Specification.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
