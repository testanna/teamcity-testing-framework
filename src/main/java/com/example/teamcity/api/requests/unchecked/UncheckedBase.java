package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedBase extends Request implements CrudInterface {
    private String endpoint;

    private static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private final static String USER_ENDPOINT = "/app/rest/users";

    public UncheckedBase(RequestSpecification spec, Class<?> type) {
        super(spec);

        switch (type.getSimpleName()) {
            case "BuildType":
                endpoint = BUILD_CONFIG_ENDPOINT;
                break;
            case "Project":
                endpoint = PROJECT_ENDPOINT;
                break;
            case "User":
                endpoint = USER_ENDPOINT;
                break;
        }
    }

    @Override
    public Response create(Object object) {
        Response res = given()
                .spec(spec)
                .body(object)
                .post(endpoint);

        if (res.statusCode() == 200) {
            TestDataStorage.getStorage().addCreatedEntity(object);
        }

        return res;
    }

    @Override
    public Response get(String id) {
        return given().spec(spec).get(endpoint + "/id:" + id);
    }

    @Override
    public Response update(Object object) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(endpoint + "/id:" + id);
    }

    public Response delete(String locatorName, String locatorValue) {
        return given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/" + locatorName + ":" + locatorValue);
    }


}