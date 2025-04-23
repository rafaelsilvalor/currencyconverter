package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup
        ConfigLoader config = new ConfigLoader();
        ExchangeRateService service = new ExchangeRateClient(config);
        CurrencyConverter converter = new CurrencyConverter(service);

        // User Input
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.print("From currency (e.g., USD): ");
        String from = scanner.nextLine().toUpperCase();

        System.out.print("To currency (e.g., BRL): ");
        String to = scanner.nextLine().toUpperCase();

        // Conversion
        double result = converter.convert(amount, from, to);
        System.out.printf("%.2f %s = %.2f %s%n", amount, from, result, to);
    }
}
