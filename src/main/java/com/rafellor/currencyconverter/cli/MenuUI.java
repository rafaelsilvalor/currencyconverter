package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;

import java.util.Scanner;

public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ExchangeRateService client;

    public MenuUI(CurrencyConverter converter, ExchangeRateService client) {
        this.converter = converter;
        this.client = client;
    }

    public void start() {
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> handleFavorites();
                case "2" -> handleConversion();
                case "3" -> handleList();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==== Currency Converter ====");
        System.out.println("1) Favorites");
        System.out.println("2) Convert currency");
        System.out.println("3) List supported currencies");
        System.out.println("0) Exit");
        System.out.println("Select an option: ");
    }

    private void handleFavorites() {
        FavoritesManager favoritesManager = new FavoritesManager("favorites.properties");
        new FavoritesMenuUI(converter, favoritesManager).start();
    }

    private void handleConversion() {
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("From (e.g. USD): ");
        String from = scanner.nextLine().toUpperCase();
        System.out.print("To (e.g. BRL: ");
        String to = scanner.nextLine().toUpperCase();

        double result = converter.convert(amount,from, to);
        System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);
    }

    private void handleList() {
        client.getSupportedCodes().forEach(System.out::println);
    }
}
