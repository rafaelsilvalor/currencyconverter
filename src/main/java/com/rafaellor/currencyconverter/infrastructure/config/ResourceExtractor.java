package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

/**
 * Extracts default resource files from the classpath into external folders on first run.
 */
public class ResourceExtractor {
    private static final List<String> DEFAULT_RESOURCES = Arrays.asList(
            "application.properties",
            "config.properties",
            "default-settings.properties",
            "messages_en_US.properties",
            "messages_pt_BR.properties",
            "simplelogger.properties"
    );

    /**
     * Extract defaults into 'settings' directory under current working directory.
     */
    public static void extractDefaults() {
        extractDefaults(Path.of(".").toAbsolutePath());
    }

    /**
     * Extract defaults into 'settings' (and data if needed) under the given base directory.
     * Only copies files that do not already exist.
     *
     * @param baseDir root directory under which 'settings' and 'data' reside
     */
    public static void extractDefaults(Path baseDir) {
        Path settingsDir = baseDir.resolve("settings");
        try {
            for (String resourceName : DEFAULT_RESOURCES) {
                Path target = settingsDir.resolve(resourceName);
                if (Files.notExists(target)) {
                    try (InputStream in = ResourceExtractor.class.getClassLoader().getResourceAsStream(resourceName)) {
                        if (in == null) {
                            System.err.println("[INIT] Resource not found in classpath: " + resourceName);
                            continue;
                        }
                        Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("[INIT] Extracted default resource: " + resourceName + " to " + target);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract default resources", e);
        }
    }
}
