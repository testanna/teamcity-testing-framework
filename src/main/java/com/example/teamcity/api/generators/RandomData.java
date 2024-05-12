package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private static final int LENGTH = 10;

    public static String getString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getString(int length) {
        return "test_" + RandomStringUtils.randomAlphabetic(length - 5);
    }
}
