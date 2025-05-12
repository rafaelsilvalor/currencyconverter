package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads configuration from:
 *   1) Classpath config.properties (defaults)
 *   2) External settings/config.properties (overrides)
 *
 * If "config.verbose=true" is set in the external file, will print load diagnostics.
 */
public class ConfigLoader {
    private static final String SETTINGS_DIR     = "settings";
    private static final String CONFIG_FILE_NAME = "config.properties";

    private final Properties properties = new Properties();
    private final boolean verbose;

    public ConfigLoader() {
        // 1. Determine verbosity by peeking at external file
        Path externalPath = Paths.get(SETTINGS_DIR, CONFIG_FILE_NAME);
        boolean extExists = Files.exists(externalPath);
        Properties extPeek = new Properties();
        if (extExists) {
            try (InputStream in = Files.newInputStream(externalPath)) {
                extPeek.load(in);
            } catch (IOException ignored) {
                // ignore peek failures
            }
        }
        this.verbose = Boolean.parseBoolean(extPeek.getProperty("config.verbose", "false"));

        // 2. Load classpath defaults
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (in != null) {
                properties.load(in);
                if (verbose) {
                    System.out.println("[CONFIG] Loaded classpath defaults: " + CONFIG_FILE_NAME);
                }
            } else if (verbose) {
                System.err.println("[CONFIG] No classpath config.properties found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load classpath config", e);
        }

        // 3. Overlay external overrides
        if (extExists) {
            try (InputStream in = Files.newInputStream(externalPath)) {
                properties.load(in);
                if (verbose) {
                    System.out.println("[CONFIG] Loaded external config: " + externalPath.toAbsolutePath());
                }
            } catch (IOException e) {
                if (verbose) {
                    System.err.println("[CONFIG] Failed to load external config: " + e.getMessage());
                }
            }
        }
    }

    /** Always returns a new loader so tests can drive fresh state. */
    public static ConfigLoader getInstance() {
        return new ConfigLoader();
    }

    /** Retrieves a required key or throws. */
    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing configuration key: " + key);
        }
        return value;
    }

    /** Retrieves a key with a default fallback. */
    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
