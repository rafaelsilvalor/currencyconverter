package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
import com.rafaellor.currencyconverter.infrastructure.config.FolderInitializer;
import com.rafaellor.currencyconverter.infrastructure.config.PathsConfig;
import com.rafaellor.currencyconverter.infrastructure.config.ResourceExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize folders and defaults
        try {
            FolderInitializer.initialize();
            ResourceExtractor.extractDefaults();
        } catch (RuntimeException e) {
            // always continue
        }

        // 2. Load config.properties (for language/country and config.verbose)
        ConfigLoader config = ConfigLoader.getInstance();
        boolean verbose = Boolean.parseBoolean(config.getOrDefault("config.verbose", "false"));

        // 3. Load settings.properties
        Path settingsPath = Paths.get(
                config.getOrDefault("language.settings.file", "settings/settings.properties")
        );
        Properties settings = new Properties();
        if (Files.exists(settingsPath)) {
            try (InputStream in = Files.newInputStream(settingsPath)) {
                settings.load(in);
            } catch (IOException e) {
                if (verbose) System.err.println("Warning: Failed to read settings. Using defaults.");
            }
        } else {
            if (verbose) System.err.println("Warning: Settings file not found. Using defaults.");
        }

        // 4. Determine locale
        String lang    = settings.getProperty("language", "en");
        String country = settings.getProperty("country",  "US");
        Locale locale  = new Locale(lang, country);

        // 5. Load messages bundle
        ResourceBundle messages;
        Path extBundle = Paths.get("settings", "messages_" + lang + "_" + country + ".properties");
        if (Files.exists(extBundle)) {
            try (InputStream in = Files.newInputStream(extBundle)) {
                messages = new PropertyResourceBundle(in);
            } catch (IOException e) {
                messages = ResourceBundle.getBundle("messages", locale);
            }
        } else {
            messages = ResourceBundle.getBundle("messages", locale);
        }

        // 6. Dispatch commands
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages);
        dispatcher.handle(args);
    }
}
