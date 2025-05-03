package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.rafellor.currencyconverter.cli.util.ConsoleUtils.clearConsole;
import static com.rafellor.currencyconverter.cli.util.ConsoleUtils.waitForUser;

public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ExchangeRateService client;
    private final ResourceBundle messages;

    public MenuUI(CurrencyConverter converter, ExchangeRateService client, ResourceBundle messages) {
        this.converter = converter;
        this.client = client;
        this.messages = messages;
    }

    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> openFavoritesMenu();
                case "2" -> handleConversion();
                case "3" -> handleList();
                case "4" -> openSettingsMenu();
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
        System.out.println("3) " + messages.getString("menu.list"));
        System.out.println("4) " + messages.getString("menu.settings"));
        System.out.println("0) " + messages.getString("menu.exit")+"\n");
        System.out.print(messages.getString("menu.prompt") + " ");
    }

    private void openFavoritesMenu() {
        FavoritesManager favoritesManager = new FavoritesManager("favorites.properties");
        FavoritesMenuUI favoritesMenuUI = new FavoritesMenuUI(converter, client, favoritesManager, messages);
        clearConsole();
        favoritesMenuUI.start();
    }

    private void handleConversion() {
        clearConsole();
        System.out.println("\n==== " + messages.getString("menu.title") + " ====\n");
        System.out.print(messages.getString("prompt.amount")+" ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print(messages.getString("prompt.from")+" ");
        String from = scanner.nextLine().toUpperCase();
        System.out.print(messages.getString("prompt.to")+" ");
        String to = scanner.nextLine().toUpperCase();
        System.out.println();
        double result = converter.convert(amount, from, to);
        System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);
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

}
