package com.rafaellor.currencyconverter.infrastructure.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Consolidated tests for ResourceExtractor:
 * - SETTINGS resources
 * - DATA languages only
 * - SCRIPT resources
 */
class ResourceExtractorTest {
    private Path tempBase;

    @AfterEach
    void cleanup() throws IOException {
        if (tempBase != null && Files.exists(tempBase)) {
            Files.walk(tempBase)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
    }

    @Test
    void extractDefaults_populatesSettingsResourcesOnly() throws IOException {
        tempBase = Files.createTempDirectory("extractTest");
        Path settingsDir = tempBase.resolve("settings");
        Files.createDirectories(settingsDir);

        ResourceExtractor.extractDefaults(tempBase);

        List<String> expected = List.of(
                "config.properties",
                "paths.properties"
        );

        List<String> actual = Files.list(settingsDir)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());

        assertEquals(expected.size(), actual.size(), "Settings directory should contain only default settings resources");
        assertTrue(actual.containsAll(expected), "Settings resources mismatch");
    }

    @Test
    void extractDefaults_populatesOnlyLanguageResources() throws IOException {
        tempBase = Files.createTempDirectory("extractTest");
        Path dataDir = tempBase.resolve("data");
        Files.createDirectories(dataDir);

        ResourceExtractor.extractDefaults(tempBase);

        // Data root should contain only 'languages' directory
        try (var stream = Files.list(dataDir)) {
            List<Path> children = stream.collect(Collectors.toList());
            assertEquals(1, children.size(), "Data folder should contain only the languages directory");
            assertEquals("languages", children.get(0).getFileName().toString(), "Data folder should contain 'languages'");
        }

        // Language files
        Path langDir = dataDir.resolve("languages");
        List<String> langExpected = List.of(
                "messages_en_US.properties",
                "messages_pt_BR.properties"
        );
        List<String> langActual = Files.list(langDir)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        assertEquals(langExpected.size(), langActual.size(), "Language folder contents mismatch");
        assertTrue(langActual.containsAll(langExpected), "Language resources mismatch");
    }

    @Test
    void extractDefaults_populatesScriptResources() throws IOException {
        tempBase = Files.createTempDirectory("extractTest");
        Path scriptsDir = tempBase.resolve("scripts");
        Files.createDirectories(scriptsDir);

        ResourceExtractor.extractDefaults(tempBase);

        List<String> scriptExpected = List.of(
                "install-cvc.bat",
                "install-cvc.ps1",
                "install-cvc.sh"
        );
        List<String> scriptActual = Files.list(scriptsDir)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        assertEquals(scriptExpected.size(), scriptActual.size(), "Scripts folder contents mismatch");
        assertTrue(scriptActual.containsAll(scriptExpected), "Script resources mismatch");
    }
}
