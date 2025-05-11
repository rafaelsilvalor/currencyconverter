package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * Extracts default resource files from the classpath into structured external folders on first run.
 */
public class ResourceExtractor {
    private static final List<String> SETTINGS_RESOURCES = List.of(
            "application.properties",
            "config.properties",
            "paths.properties"
    );

    private static final List<String> DATA_ROOT_RESOURCES = List.of(
            "favorites.properties",
            "Conversion-history.txt"
    );

    private static final List<String> DATA_LANG_RESOURCES = List.of(
            "messages_en_US.properties",
            "messages_pt_BR.properties"
    );

    private static final List<String> SCRIPT_RESOURCES = List.of(
            "install-cvc.bat",
            "install-cvc.ps1",
            "install-cvc.sh"
    );

    /**
     * Extract defaults into structured folders ('settings', 'data', 'scripts') under current directory.
     */
    public static void extractDefaults() {
        extractDefaults(Path.of(".").toAbsolutePath());
    }

    public static void extractDefaults(Path baseDir) {
        // Settings
        Path settingsDir = baseDir.resolve("settings");
        SETTINGS_RESOURCES.forEach(name -> extractResource(settingsDir, name));

        // Data root files
        Path dataDir = baseDir.resolve("data");
        DATA_ROOT_RESOURCES.forEach(name -> extractResource(dataDir, name));

        // Data language files
        Path langDir = dataDir.resolve("languages");
        DATA_LANG_RESOURCES.forEach(name -> extractResource(langDir, name));

        // Scripts
        Path scriptsDir = baseDir.resolve("scripts");
        SCRIPT_RESOURCES.forEach(name -> extractResource(scriptsDir, name));
    }

    private static void extractResource(Path dir, String resourceName) {
        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
                System.out.println("✅ [INIT] Created directory: " + dir);
            }
            Path target = dir.resolve(resourceName);
            if (Files.exists(target)) {
                System.out.println("⚙️  [INIT] Resource already exists, skipping: " + target);
                return;
            }
            try (InputStream in = ResourceExtractor.class.getClassLoader().getResourceAsStream(resourceName)) {
                if (in == null) {
                    System.err.println("⚠️  [INIT] Resource missing in JAR: " + resourceName);
                    return;
                }
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ [INIT] Extracted " + resourceName + " to " + dir);
            }
        } catch (IOException e) {
            System.err.println("❌ [INIT] Failed to extract " + resourceName + " to " + dir + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
