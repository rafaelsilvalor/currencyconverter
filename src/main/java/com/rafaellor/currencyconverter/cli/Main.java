package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.infrastructure.config.FolderInitializer;
import com.rafaellor.currencyconverter.infrastructure.config.ResourceExtractor;
import com.rafaellor.currencyconverter.infrastructure.config.PathsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        // Step 3: Ensure external folders and default resources
        try {
            FolderInitializer.initialize();
            ResourceExtractor.extractDefaults();
        } catch (RuntimeException e) {
            System.err.println("Error during initial setup: " + e.getMessage());
            // Proceeding with startup even if setup fails
        }

        // Determine settings file location, with fallback if not configured
        String settingsFilePath;
        try {
            settingsFilePath = PathsConfig.getInstance().get("language.settings.file");
        } catch (IllegalArgumentException e) {
            // key missing → fall back to default location
            settingsFilePath = "settings/settings.properties";
        }

        Path settingsPath = Paths.get(settingsFilePath);
        Properties settings = new Properties();

        if (Files.exists(settingsPath)) {
            try (InputStream in = Files.newInputStream(settingsPath)) {
                settings.load(in);
            } catch (IOException e) {
                System.err.println("Warning: Failed to read settings. Using defaults.");
            }
        } else {
            System.err.println("Warning: Settings file not found. Using defaults.");
        }

        // Initialize Locale & ResourceBundle
        String lang    = settings.getProperty("language", "en");
        String country = settings.getProperty("country",  "US");
        Locale locale  = new Locale(lang, country);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // Instantiate dispatcher (auto-discovers ALL handlers)
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages);

        // Hand off to the dispatcher
        dispatcher.handle(args);
    }
}
