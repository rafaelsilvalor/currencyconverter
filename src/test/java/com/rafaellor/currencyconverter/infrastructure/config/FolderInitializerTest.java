package com.rafaellor.currencyconverter.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FolderInitializerTest {
    private Path tempDir;

    @Test
    void initialize_createsSettingsAndDataDirectories() throws IOException {
        tempDir = Files.createTempDirectory("testBase");

        // Act
        FolderInitializer.initialize(tempDir);

        // Assert
        assertTrue(Files.isDirectory(tempDir.resolve("settings")), "settings dir should be created");
        assertTrue(Files.isDirectory(tempDir.resolve("data")), "data dir should be created");
        assertTrue(Files.isDirectory(tempDir.resolve("scripts")), "scripts dir should be created");
    }

    @AfterEach
    void cleanup() throws IOException {
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
    }
}
