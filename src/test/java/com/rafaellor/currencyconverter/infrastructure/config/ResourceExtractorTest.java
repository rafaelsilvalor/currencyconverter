package com.rafaellor.currencyconverter.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceExtractorTest {
    private Path tempBase;

    @Test
    void extractDefaults_populatesSettingsWithAllDefaultFiles() throws IOException {
        tempBase = Files.createTempDirectory("extractTest");
        Path settingsDir = tempBase.resolve("settings");
        Files.createDirectories(settingsDir);

        // Act
        ResourceExtractor.extractDefaults(tempBase);

        // Assert: each default resource should now exist
        List<String> expected = List.of(
                "application.properties",
                "config.properties",
                "default-settings.properties",
                "messages_en_US.properties",
                "messages_pt_BR.properties",
                "simplelogger.properties"
        );

        for (String name : expected) {
            Path file = settingsDir.resolve(name);
            assertTrue(Files.exists(file), "Expected resource " + name + " to be extracted");
            assertTrue(Files.size(file) > 0, "Extracted resource " + name + " should not be empty");
        }
    }

    @AfterEach
    void cleanup() throws IOException {
        if (tempBase != null && Files.exists(tempBase)) {
            Files.walk(tempBase)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
    }
}
