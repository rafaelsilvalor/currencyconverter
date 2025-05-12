package com.rafaellor.currencyconverter.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {
    private Path tempSettings;

    @AfterEach
    void cleanup() throws IOException {
        // Remove temp settings directory if it exists
        if (tempSettings != null && Files.exists(tempSettings)) {
            Files.walk(tempSettings)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
        // Clear any loaded properties by discarding the instance
        // (getInstance always reloads now)
    }

    @Test
    void externalConfigIsLoadedWhenPresent() throws IOException {
        // Arrange: create settings/config.properties
        tempSettings = Files.createTempDirectory("settings");
        Path settingsDir = tempSettings;
        Path configFile = settingsDir.resolve("config.properties");
        Files.createDirectories(settingsDir);
        try (FileWriter fw = new FileWriter(configFile.toFile())) {
            fw.write("key1=externalValue\n");
        }
        // move into place
        Path orig = Path.of("settings");
        orig.toFile().mkdirs();
        Files.copy(configFile, orig.resolve("config.properties"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Act
        ConfigLoader loader = ConfigLoader.getInstance();

        // Assert
        assertEquals("externalValue", loader.get("key1"));
    }

    @Test
    void getThrowsWhenKeyMissingAndNoExternalConfig() throws IOException {
        // Ensure no external settings folder
        Path settingsDir = Path.of("settings");
        if (Files.exists(settingsDir)) {
            Files.walk(settingsDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        ConfigLoader loader = ConfigLoader.getInstance();
        assertThrows(IllegalArgumentException.class, () -> loader.get("key2"));
    }

    @Test
    void getOrDefaultReturnsDefaultIfMissing() {
        // Ensure no external file
        File extDir = new File("settings");
        if (extDir.exists()) {
            extDir.delete();
        }

        // Act
        ConfigLoader loader = ConfigLoader.getInstance();

        // keyNotExists should return the provided default
        assertEquals("myDefault", loader.getOrDefault("keyNotExists", "myDefault"));
    }
}
