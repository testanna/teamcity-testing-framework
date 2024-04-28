package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.model.User;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser extends Request implements CrudInterface {

    public CheckedUser(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public User create(Object object) {
        return new UncheckedUser(spec)
                .create(object)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);
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
        return new UncheckedUser(spec)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
