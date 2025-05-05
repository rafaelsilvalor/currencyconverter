package com.rafellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        loadResource("application.properties");
        loadResource("config.properties");
    }

    private static void loadResource(String resourceName) {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourceName)) {

            if (in != null) {
                props.load(in);
            } else {
                System.err.printf(
                        "Warning: %s not found on classpath%n", resourceName
                );
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to load " + resourceName, e
            );
        }
    }


    public static String get(String key) {
        return props.getProperty(key);
    }


    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
