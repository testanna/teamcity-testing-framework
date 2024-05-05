package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedBase extends Request implements CrudInterface {
    private final Class<?> responseType;

    public CheckedBase(RequestSpecification spec, Class<?> responseType) {
        super(spec);
        this.responseType = responseType;
    }

    @Override
    public Object create(Object object) {
        return new UncheckedBase(spec, responseType)
                .create(object)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(responseType);
    }

    @Override
    public Object get(String id) {
        return new UncheckedBase(spec, responseType)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(responseType);
    }

    @Override
    public Object update(Object object) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedBase(spec, responseType)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
