package com.rafaellor.currencyconverter.cli;

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
        // Load user settings
        Path settingsPath = Paths.get("settings/settings.properties");
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

        // Instantiate dispatcher (auto‑discovers ALL handlers) ──────────
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages);

        //  Hand off to the dispatcher
        dispatcher.handle(args);
    }
}
