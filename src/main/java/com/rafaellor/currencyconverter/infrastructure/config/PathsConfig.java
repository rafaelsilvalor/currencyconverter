package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads path configuration from an external settings folder or falls back to classpath.
 */
public class PathsConfig {
    private static final String SETTINGS_DIR = "settings";
    private static final String PATHS_FILE_NAME = "paths.properties";

    private final Properties properties = new Properties();

    private PathsConfig() {
        loadProperties();
    }

    private void loadProperties() {
        Path externalPaths = Paths.get(SETTINGS_DIR).resolve(PATHS_FILE_NAME);
        if (Files.exists(externalPaths)) {
            try (InputStream in = Files.newInputStream(externalPaths)) {
                properties.load(in);
                System.out.println("[CONFIG] Loaded external paths config: " + externalPaths.toAbsolutePath());
                return;
            } catch (IOException e) {
                System.err.println("[CONFIG] Failed to load external paths, falling back to classpath: " + e.getMessage());
            }
        }
        // fallback to classpath
        try (InputStream in = PathsConfig.class.getClassLoader().getResourceAsStream(PATHS_FILE_NAME)) {
            if (in != null) {
                properties.load(in);
                System.out.println("[CONFIG] Loaded classpath paths config: " + PATHS_FILE_NAME);
            } else {
                System.err.println("[CONFIG] No paths.properties found on classpath or in settings directory.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load classpath paths: " + PATHS_FILE_NAME, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing path key: " + key);
        }
        return value;
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static PathsConfig getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PathsConfig INSTANCE = new PathsConfig();
    }
}
