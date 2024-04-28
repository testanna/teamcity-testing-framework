package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.model.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {
    private static Specification spec;

    private Specification() {
    }

    public static Specification getSpec() {
        if (spec == null) {
            spec = new Specification();
        }
        return spec;
    }

    private RequestSpecBuilder requestSBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification unauthSpec() {
        var requestBuilder = requestSBuilder();
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = requestSBuilder();
        requestBuilder.setBaseUri("http://" + user.getUsername() + ":" + user.getPassword() + "@"
                + Config.getProperty("host"));
        return requestBuilder.build();
    }

    public RequestSpecification superUserSpec() {
        var requestBuilder = requestSBuilder();
        requestBuilder
                .setBaseUri("http://" + ":" + Config.getProperty("superUserToken") + "@" + Config.getProperty("host"));
        return requestBuilder.build();
    }
}
