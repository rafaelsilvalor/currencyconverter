package com.rafaellor.currencyconverter.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static com.rafaellor.currencyconverter.cli.util.ConsoleUtils.clearConsole;
import static com.rafaellor.currencyconverter.cli.util.ConsoleUtils.waitForUser;

public class SettingsMenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final Properties settings;
    private final String settingsFile = "settings/settings.properties";
    private final ResourceBundle messages;

    public SettingsMenuUI(Properties settings, ResourceBundle messages) {
        this.settings = settings;
        this.messages = messages;
    }

    public void start() {
        while (true) {
            clearConsole();
            System.out.println("\n==== " + messages.getString("settings.title") + " ====\n");
            System.out.println("1) " + messages.getString("settings.language"));
            System.out.println("0) " + messages.getString("settings.back")+ "\n");
            System.out.print(messages.getString("menu.prompt") + " ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> changeLanguage();
                case "0" -> {
                    return;
                }
                default -> System.out.println(messages.getString("error.invalid.option"));
            }
        }
    }

    private void changeLanguage() {
        while (true) {
            clearConsole();
            System.out.println("\n==== " + messages.getString("settings.languages.available") + " ====\n");
            System.out.println("1) English (en-US)");
            System.out.println("2) Português (pt-BR)");
            System.out.println("0) " + messages.getString("settings.back") + "\n");
            System.out.print(messages.getString("menu.prompt") + " ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    saveLocale("en", "US");
                    return;
                }
                case "2" -> {
                    saveLocale("pt", "BR");
                    return;
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println(messages.getString("error.invalid.option"));
            }
            waitForUser(messages.getString("prompt.continue"));
        }
    }

    private void saveLocale(String language, String country) {
        settings.setProperty("language", language);
        settings.setProperty("country", country);

        try {
            File file = new File(settingsFile);

            // Ensure the 'settings' folder exists
            file.getParentFile().mkdirs();

            try (OutputStream out = new FileOutputStream(file)) {
                settings.store(out, "Currency Converter Settings");
            }

            System.out.println(messages.getString("settings.language.saved"));
            System.out.println(messages.getString("settings.restart.required"));
        } catch (IOException e) {
            System.out.println(messages.getString("settings.language.error") + ": " + e.getMessage());
        }
    }
}
