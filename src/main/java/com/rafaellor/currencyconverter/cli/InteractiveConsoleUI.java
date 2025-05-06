package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;

import java.util.ResourceBundle;
import java.util.Scanner;

public class InteractiveConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ResourceBundle messages;

    public InteractiveConsoleUI(CurrencyConverter converter, ResourceBundle messages) {
        this.converter = converter;
        this.messages = messages;
    }

    public void start() {
        try {
            System.out.print(messages.getString("prompt.amount"));
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print(messages.getString("prompt.from"));
            String from = scanner.nextLine().toUpperCase();

            System.out.print(messages.getString("prompt.to"));
            String to = scanner.nextLine().toUpperCase();

            double result = converter.convert(amount, from, to);
            System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);
        } catch (NumberFormatException e) {
            System.out.println(messages.getString("favorites.invalid.amount"));
        } catch (RuntimeException e) {
            System.out.println(messages.getString("error.cli") + ": " + e.getMessage());
        }
    }
}
