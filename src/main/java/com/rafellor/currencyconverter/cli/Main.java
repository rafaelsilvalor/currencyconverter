package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== Main launched with args: " + Arrays.toString(args));
        ConfigLoader config = new ConfigLoader();
        ExchangeRateService service = new ExchangeRateClient(config);
        CurrencyConverter converter = new CurrencyConverter(service);

        // CLI Mode
        if (args.length > 0) {
            System.out.println("== CLI Mode Activated");
            try {
                var command = CommandLineParser.parse(args);
                System.out.printf(">> Converting %.2f %s -> %s%n", command.amount(), command.from(), command.to());

                double result = converter.convert(command.amount(), command.from(), command.to());

                System.out.printf("== %.2f %s == %.2f %s%n",
                        command.amount(), command.from(), result, command.to());
                return;
            } catch (Exception e) {
                System.out.println("!! CLI Error: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        // Interactive Mode
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
        } catch (InputMismatchException e) {
            System.out.println("!! Invalid input: please enter a valid number.");
        } catch (RuntimeException e) {
            System.out.println("!! Error during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
