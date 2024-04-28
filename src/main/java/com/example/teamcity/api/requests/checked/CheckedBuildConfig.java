package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.model.BuildType;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedBuildConfig extends Request implements CrudInterface {
    public CheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public BuildType create(Object object) {
        return new UncheckedBuildConfig(spec).create(object)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object object) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedBuildConfig(spec).delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
