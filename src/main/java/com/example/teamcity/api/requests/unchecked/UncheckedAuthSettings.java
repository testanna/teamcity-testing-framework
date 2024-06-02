package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.spec.Specification;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UncheckedAuthSettings extends Request {
    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";
    private final static String AUTH_SETTINGS_FILE = "authsettings.json";

    public UncheckedAuthSettings() {
        super(Specification.getSpec().superUserSpec());
    }

    /**
     * Set AuthSettings perProjectPermissions = true
     * Required for user roles testing
     * TODO Create AuthSettings model for setting authentication parameters
     */
    public void setDefaultAuthSettings() {
        try (var authSettings = UncheckedAuthSettings.class.getClassLoader()
                .getResourceAsStream(AUTH_SETTINGS_FILE)) {
            given().filter(new SwaggerCoverageRestAssured())
                    .spec(Specification.getSpec().superUserSpec()).body(authSettings)
                    .put(AUTH_SETTINGS_ENDPOINT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
