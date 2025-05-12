package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads configuration properties from an external settings folder or falls back to classpath.
 * Always creates a fresh instance to support reloading in tests.
 */
public class ConfigLoader {
    private static final String SETTINGS_DIR = "settings";
    private static final String CONFIG_FILE_NAME = "config.properties";

    private final Properties properties = new Properties();

    public ConfigLoader() {
        loadProperties();
    }

    private void loadProperties() {
        Path externalConfig = Paths.get(SETTINGS_DIR).resolve(CONFIG_FILE_NAME);
        if (Files.exists(externalConfig)) {
            try (InputStream in = Files.newInputStream(externalConfig)) {
                properties.load(in);
                System.out.println("[CONFIG] Loaded external config: " + externalConfig.toAbsolutePath());
                return;
            } catch (IOException e) {
                System.err.println("[CONFIG] Failed to load external config, falling back to classpath: " + e.getMessage());
            }
        }
        // fallback to classpath
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (in != null) {
                properties.load(in);
                System.out.println("[CONFIG] Loaded classpath config: " + CONFIG_FILE_NAME);
            } else {
                System.err.println("[CONFIG] No config.properties found on classpath or in settings directory.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load classpath config: " + CONFIG_FILE_NAME, e);
        }
    }

    /**
     * Always returns a new instance, supporting multiple loads (useful for tests).
     */
    public static ConfigLoader getInstance() {
        return new ConfigLoader();
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing configuration key: " + key);
        }
        return value;
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
