package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ExchangeRateService client;
    private final ResourceBundle messages;

    public MenuUI(CurrencyConverter converter, ExchangeRateService client) {
        this.converter = converter;
        this.client = client;
        this.messages = ResourceBundle.getBundle("messages", Locale.getDefault()); // auto-detects pt-BR, en, etc.
    }

    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> openFavoritesMenu();
                case "2" -> handleConversion();
                case "3" -> handleList();
                case "0" -> {
                    System.out.println(messages.getString("menu.goodbye"));
                    return;
                }
                default -> System.out.println(messages.getString("menu.invalid"));
            }
        }
    }

    private void printMenu() {
        clearConsole();
        System.out.println("\n==== " + messages.getString("menu.title") + " ====\n");
        System.out.println("1) " + messages.getString("menu.option.favorites"));
        System.out.println("2) " + messages.getString("menu.option.convert"));
        System.out.println("3) " + messages.getString("menu.option.list"));
        System.out.println("0) " + messages.getString("menu.option.exit") + "\n");
        System.out.print(messages.getString("menu.select"));
    }

    private void openFavoritesMenu() {
        FavoritesManager favoritesManager = new FavoritesManager("favorites.properties");
        FavoritesMenuUI favoritesMenuUI = new FavoritesMenuUI(converter, favoritesManager);
        clearConsole();
        favoritesMenuUI.start();
    }

    private void handleConversion() {
        System.out.print(messages.getString("prompt.amount"));
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print(messages.getString("prompt.from"));
        String from = scanner.nextLine().toUpperCase();

        System.out.print(messages.getString("prompt.to"));
        String to = scanner.nextLine().toUpperCase();

        double result = converter.convert(amount, from, to);
        System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);

        waitForUser(messages.getString("prompt.continue"));
    }

    private void handleList() {
        client.getSupportedCodes().forEach(System.out::println);
        waitForUser(messages.getString("prompt.continue"));
    }

    private void waitForUser(String message) {
        System.out.print(message);
        scanner.nextLine();
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }
}
