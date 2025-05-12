package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SetApiKeyHandlerTest {
    private Path settingsDir;
    private Path configFile;

    @BeforeEach
    void setUp() throws Exception {
        // ensure a clean settings folder next to project root
        settingsDir = Path.of("settings");
        if (Files.exists(settingsDir)) {
            Files.walk(settingsDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
        Files.createDirectories(settingsDir);
        configFile = settingsDir.resolve("config.properties");
    }

    @AfterEach
    void tearDown() throws Exception {
        if (Files.exists(settingsDir)) {
            Files.walk(settingsDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(p -> p.toFile().delete());
        }
    }

    @Test
    void matchesOnlySetApiKeyFlag() {
        ResourceBundle dummy = ResourceBundle.getBundle("config"); // any bundle
        SetApiKeyHandler handler = new SetApiKeyHandler(dummy);

        assertTrue(handler.matches(new String[] {"--set-apikey", "ABC123"}));
        assertFalse(handler.matches(new String[] {"--setapikey", "ABC123"}));
        assertFalse(handler.matches(new String[] {"--set-apikey"}));
    }

    @Test
    void executeWritesApiKeyToConfig() throws Exception {
        ResourceBundle dummy = ResourceBundle.getBundle("config");
        SetApiKeyHandler handler = new SetApiKeyHandler(dummy);

        String testKey = "SECRET_KEY_987";
        handler.execute(new String[] {"--set-apikey", testKey});

        // now reload the properties
        assertTrue(Files.exists(configFile), "config.properties should exist");
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(configFile.toFile())) {
            props.load(in);
        }
        assertEquals(testKey, props.getProperty("API_KEY"), "API key should be stored under 'api.key'");
    }
}
