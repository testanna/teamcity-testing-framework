package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.model.User;
import com.example.teamcity.api.spec.Specification;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public class AuthRequest {
    private final User user;

    public AuthRequest(User user) {
        this.user = user;
    }

    public String getCsrfToken() {
        return RestAssured
                .given()
                .spec(Specification.getSpec().authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
