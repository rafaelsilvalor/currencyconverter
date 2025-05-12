package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads path-related properties from:
 * 1) External settings/paths.properties
 * 2) Classpath paths.properties
 *
 * Optional verbose logging controlled by config.verbose in settings/config.properties.
 */
public class PathsConfig {
    private static final String SETTINGS_DIR     = "settings";
    private static final String PATHS_FILE_NAME  = "paths.properties";

    private final Properties properties = new Properties();
    private final boolean verbose;

    public PathsConfig() {
        // First load config.properties to get config.verbose
        this.verbose = Boolean.parseBoolean(
                ConfigLoader.getInstance().getOrDefault("config.verbose", "false")
        );

        // Then load paths.properties
        Path external = Paths.get(SETTINGS_DIR, PATHS_FILE_NAME);
        if (Files.exists(external)) {
            try (InputStream in = Files.newInputStream(external)) {
                properties.load(in);
                if (verbose) System.out.println("[CONFIG] Loaded external paths config: " + external.toAbsolutePath());
                return;
            } catch (IOException ignored) {}
        }

        // Fallback to classpath
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PATHS_FILE_NAME)) {
            if (in != null) properties.load(in);
            if (verbose) System.out.println("[CONFIG] Loaded classpath paths config: " + PATHS_FILE_NAME);
        } catch (IOException ignored) {}
    }

    public static PathsConfig getInstance() {
        return new PathsConfig();
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new IllegalArgumentException("Missing path key: " + key);
        return value;
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
