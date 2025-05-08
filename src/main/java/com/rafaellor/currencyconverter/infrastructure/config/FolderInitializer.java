package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ensures that external configuration directories exist next to the application JAR.
 */
public class FolderInitializer {

    /**
     * Initializes the standard folders (`settings` and `data`) in the current working directory.
     */
    public static void initialize() {
        initialize(Paths.get("").toAbsolutePath());
    }

    /**
     * Initializes the standard folders (`settings` and `data`) under the given base directory.
     *
     * @param baseDir the root directory under which the folders will be created
     */
    public static void initialize(Path baseDir) {
        Path settingsDir = baseDir.resolve("settings");
        Path dataDir = baseDir.resolve("data");
        Path scriptsDir = baseDir.resolve("scripts");
        try {
            if (Files.notExists(settingsDir)) {
                Files.createDirectories(settingsDir);
                System.out.println("[INIT] Created settings directory: " + settingsDir);
            }
            if (Files.notExists(dataDir)) {
                Files.createDirectories(dataDir);
                System.out.println("[INIT] Created data directory: " + dataDir);
            }
            if (Files.notExists(scriptsDir)) {
                Files.createDirectories(scriptsDir);
                System.out.println("[INIT] Created scripts directory: " + scriptsDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize folders", e);
        }
    }
}
