package com.rafaellor.currencyconverter.infrastructure.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Ensures that external configuration directories exist next to the application JAR.
 * Uses a guard-clause / fail-first style to avoid else statements.
 */
public class FolderInitializer {

    /**
     * Initializes the standard folders (`settings`, `data`, and `scripts`) in the current working directory.
     */
    public static void initialize() {
        initialize(Path.of(".").toAbsolutePath());
    }

    /**
     * Initializes the standard folders under the given base directory.
     * Uses early continues to simplify logic.
     *
     * @param baseDir the root directory under which the folders will be created
     */
    public static void initialize(Path baseDir) {
        List<Path> dirs = List.of(
                baseDir.resolve("settings"),
                baseDir.resolve("data"),
                baseDir.resolve("scripts")
        );

        for (Path dir : dirs) {
            try {
                if (Files.exists(dir)) {
                    System.out.println("⚙️  [INIT] Directory already exists: " + dir);
                    continue;
                }
                Files.createDirectories(dir);
                System.out.println("✅ [INIT] Created directory: " + dir);
            } catch (IOException e) {
                System.err.println("❌ [INIT] Failed to create " + dir + ": " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}

