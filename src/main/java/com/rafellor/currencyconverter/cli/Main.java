package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        File settingsFile = new File("settings/settings.properties");
        Properties settings = new Properties();

        if (settingsFile.exists()) {
            try (FileInputStream in = new FileInputStream(settingsFile)) {
                settings.load(in);
            } catch (IOException e) {
                System.out.println("Warning: Failed to read settings. Using defaults.");
            }
        } else {
            System.out.println("Warning: Settings file not found. Using defaults.");
        }

        String lang = settings.getProperty("language", "en");
        String country = settings.getProperty("country", "US");
        Locale locale = new Locale(lang, country);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        // Instantiate history manager
        Path historyPath = Paths.get("conversion-history.txt");
        ConversionHistoryManager historyManager = new ConversionHistoryManager(historyPath);


        // Start the dispatcher
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages, historyManager);
        dispatcher.handle(args);
    }
}
