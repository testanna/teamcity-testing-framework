package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.model.Project;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject extends Request implements CrudInterface {

    public CheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Project create(Object object) {
        return new UncheckedProject(spec).create(object)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object get(String id) {
        return new UncheckedProject(spec)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(Object object) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedProject(spec)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
