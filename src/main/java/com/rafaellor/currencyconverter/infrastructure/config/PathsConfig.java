package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Singleton loader for paths defined in settings/paths.properties
 * located at the project root: currencyconverter/settings/paths.properties
 */
public class PathsConfig {
    // Relative to your project root (working directory)
    private static final String PROP_FILE_LOCATION = "settings" + File.separator + "paths.properties";
    private static final PathsConfig INSTANCE = new PathsConfig();
    private final Properties props = new Properties();

    private PathsConfig() {
        try (FileInputStream in = new FileInputStream(
                Paths.get(PROP_FILE_LOCATION).toFile()
        )) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load paths config from " + PROP_FILE_LOCATION, e
            );
        }
    }

    /** @return singleton instance */
    public static PathsConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieve a configured path by key (e.g. "conversion.history").
     *
     * @param key the property name in paths.properties
     * @return the path string (e.g. "conversion-history.txt")
     * @throws IllegalArgumentException if the key is missing
     */
    public String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return value;
    }
}
