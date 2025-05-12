package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Ensures that the external folders (settings, data, scripts) exist before application startup,
 * with optional verbose logging controlled by init.verbose.
 */
public class FolderInitializer {
    private static final String[] FOLDERS = { "settings", "data", "scripts" };

    public static void initialize() {
        Path base = Path.of(".").toAbsolutePath().normalize();

        boolean verbose = Boolean.parseBoolean(
                ConfigLoader.getInstance().getOrDefault("config.verbose", "true")
        );

        for (String folder : FOLDERS) {
            Path dir = base.resolve(folder);
            try {
                if (Files.notExists(dir)) {
                    Files.createDirectories(dir);
                    if (verbose) System.out.println("✅ [INIT] Created folder: " + dir);
                } else {
                    if (verbose) System.out.println("⚙️  [INIT] Folder already exists: " + dir);
                }
            } catch (IOException e) {
                if (verbose) System.err.println("❌ [INIT] Failed to create folder " + dir + ": " + e.getMessage());
                throw new RuntimeException("Could not initialize folder: " + dir, e);
            }
        }
    }
}
