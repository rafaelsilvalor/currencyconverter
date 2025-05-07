package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.cli.util.ConversionHandler;
import com.rafaellor.currencyconverter.cli.util.HistoryUtils;
import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.config.PathsConfig;
import com.rafaellor.currencyconverter.infrastructure.favorites.FavoritesManager;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.util.ResourceBundle;
import java.util.Scanner;

import static com.rafaellor.currencyconverter.cli.util.ConsoleUtils.clearConsole;
import static com.rafaellor.currencyconverter.cli.util.ConsoleUtils.waitForUser;

public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ExchangeRateService client;
    private final ResourceBundle messages;
    private final ConversionHistoryManager historyManager;

    public MenuUI(
            CurrencyConverter converter,
            ExchangeRateService client,
            ResourceBundle messages,
            ConversionHistoryManager historyManager
    ) {
        this.converter = converter;
        this.client = client;
        this.messages = messages;
        this.historyManager = historyManager;
    }

    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> openFavoritesMenu();
                case "2" -> handleConversion();
                case "3" -> handleRecentlyConverted();
                case "4" -> handleList();
                case "5" -> openSettingsMenu();
                case "0" -> {
                    System.out.println(messages.getString("goodbye"));
                    return;
                }
                default -> System.out.println(messages.getString("error.invalid.option"));
            }
        }
    }

    private void printMenu() {
        clearConsole();
        System.out.println("\n==== " + messages.getString("menu.title") + " ====\n");
        System.out.println("1) " + messages.getString("menu.favorites"));
        System.out.println("2) " + messages.getString("menu.convert"));
        System.out.println("3) " + messages.getString("menu.recentlyConverted"));
        System.out.println("4) " + messages.getString("menu.list"));
        System.out.println("5) " + messages.getString("menu.settings"));
        System.out.println("0) " + messages.getString("menu.exit") + "\n");
        System.out.print(messages.getString("menu.prompt") + " ");
    }

    private void openFavoritesMenu() {
        // Load the favorites file path from settings
        String favFilePath = PathsConfig.getInstance().get("favorites.file");
        FavoritesManager favoritesManager = new FavoritesManager(favFilePath);
        FavoritesMenuUI favoritesMenuUI =
                new FavoritesMenuUI(converter, client, favoritesManager, historyManager, messages);
        clearConsole();
        favoritesMenuUI.start();
    }

    private void handleConversion() {
        clearConsole();
        System.out.println("\n==== " + messages.getString("menu.title") + " ====\n");
        System.out.print(messages.getString("prompt.amount") + " ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print(messages.getString("prompt.from") + " ");
        String from = scanner.nextLine().toUpperCase();
        System.out.print(messages.getString("prompt.to") + " ");
        String to = scanner.nextLine().toUpperCase();
        System.out.println();

        ConversionHandler.perform(
                amount,
                from,
                to,
                converter,
                historyManager,
                messages
        );

        System.out.println();
        waitForUser(messages.getString("prompt.continue"));
    }

    private void handleList() {
        clearConsole();
        System.out.println("\n====== " + messages.getString("menu.list") + " ======\n");
        client.getSupportedCodes().forEach(System.out::println);
        System.out.println();
        waitForUser(messages.getString("prompt.continue") + " ");
    }

    private void openSettingsMenu() {
        var settings = new java.util.Properties();
        clearConsole();
        new SettingsMenuUI(settings, messages).start();
    }

    private void handleRecentlyConverted() {
        clearConsole();
        System.out.println("\n====== " + messages.getString("menu.recentlyConverted") + " ======\n");

        HistoryUtils.showHistory(historyManager, messages, 10);

        System.out.println();
        waitForUser(messages.getString("prompt.continue") + " ");
    }
}
