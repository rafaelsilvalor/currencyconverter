package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Extracts default resource files from the classpath into structured external folders on first run,
 * with optional verbose init logging controlled via init.verbose in config.properties.
 */
public class ResourceExtractor {
    private static final List<String> SETTINGS_RESOURCES = List.of(
            "config.properties",
            "paths.properties"
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

    // Read the verbose flag after ConfigLoader has loaded settings/config.properties
    private static final boolean VERBOSE = Boolean.parseBoolean(
            ConfigLoader.getInstance().getOrDefault("config.verbose", "true")
    );

    public static void extractDefaults() {
        extractDefaults(Path.of(".").toAbsolutePath().normalize());
    }

    public static void extractDefaults(Path baseDir) {
        // Settings folder
        Path settingsDir = baseDir.resolve("settings");
        SETTINGS_RESOURCES.forEach(name -> extractResource(settingsDir, name));

        // Data languages only
        Path dataLangDir = baseDir.resolve("data").resolve("languages");
        DATA_LANG_RESOURCES.forEach(name -> extractResource(dataLangDir, name));

        // Scripts folder
        Path scriptsDir = baseDir.resolve("scripts");
        SCRIPT_RESOURCES.forEach(name -> extractResource(scriptsDir, name));
    }

    private static void extractResource(Path dir, String resourceName) {
        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
                if (VERBOSE) System.out.println("✅ [INIT] Created directory: " + dir);
            }
            Path target = dir.resolve(resourceName);
            if (Files.exists(target)) {
                if (VERBOSE) System.out.println("⚙️  [INIT] Resource already exists, skipping: " + target);
                return;
            }
            try (InputStream in = ResourceExtractor.class
                    .getClassLoader()
                    .getResourceAsStream(resourceName)) {
                if (in == null) {
                    if (VERBOSE) System.err.println("⚠️  [INIT] Resource missing in JAR: " + resourceName);
                    return;
                }
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                if (VERBOSE) System.out.println("✅ [INIT] Extracted " + resourceName + " to " + dir);
            }
        } catch (IOException e) {
            if (VERBOSE) System.err.println("❌ [INIT] Failed to extract " + resourceName + " to " + dir + ": " + e.getMessage());
            throw new RuntimeException("Failed to extract resource " + resourceName, e);
        }
    }
}
