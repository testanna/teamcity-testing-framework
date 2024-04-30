package com.example.teamcity.api.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private final static String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private final Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties(CONFIG_PROPERTIES);
    }

    private static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void loadProperties(String fileName) {
        try (final var file = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (file == null) {
                System.err.println("File not found " + fileName);
                return;
            }
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Error during file reading " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
