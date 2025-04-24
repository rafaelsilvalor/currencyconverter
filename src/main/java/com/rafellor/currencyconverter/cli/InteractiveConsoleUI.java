package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;

import java.util.Scanner;

public class InteractiveConsoleUI {
    private final CurrencyConverter converter;

    public InteractiveConsoleUI(CurrencyConverter converter) {
        this.converter = converter;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("From currency (e.g., USD): ");
            String from = scanner.nextLine().trim().toUpperCase();

            System.out.print("To currency (e.g., BRL): ");
            String to = scanner.nextLine().trim().toUpperCase();

            double result = converter.convert(amount, from, to);
            System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);
        } catch (Exception e) {
            System.out.println("!! Error: " + e.getMessage());
        }
    }
}
