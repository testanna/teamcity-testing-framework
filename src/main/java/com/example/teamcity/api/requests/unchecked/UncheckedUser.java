package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser extends Request implements CrudInterface {
    private final static String USER_ENDPOINT = "/app/rest/users";

    public UncheckedUser(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object object) {
        return given()
                .spec(spec)
                .body(object)
                .post(USER_ENDPOINT);
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
    public Response delete(String username) {
        return given().spec(spec)
                .delete(USER_ENDPOINT + "/username:" + username);
    }
}
