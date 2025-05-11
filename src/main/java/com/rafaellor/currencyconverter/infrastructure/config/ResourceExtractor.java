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

        for (String resourceName : DEFAULT_RESOURCES) {
            Path target = settingsDir.resolve(resourceName);

            if (Files.exists(target)) {
                System.out.println("⚙️  [INIT] Resource already exists, skipping: " + resourceName);
                continue;
            }

            try (InputStream in = ResourceExtractor
                    .class
                    .getClassLoader()
                    .getResourceAsStream(resourceName))
            {
                if (in == null) {
                    System.err.println("⚠️  [INIT] Default resource missing in JAR: " + resourceName);
                    continue;
                }
                Files.copy(in, target);
                System.out.println("✅ [INIT] Extracted default resource: " + resourceName);

            } catch (IOException e) {
                System.err.println("❌ [INIT] Failed to extract " + resourceName + ": " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
