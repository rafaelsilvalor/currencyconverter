package com.rafaellor.currencyconverter.cli;

public class CommandLineParser {
    public record Command(String from, String to, double amount) {}

    public static Command parse(String[] args) {
        if (args.length == 4
                && args[0].equalsIgnoreCase("--oneline")) {
            String from = args[1].toUpperCase();
            String to = args[2].toUpperCase();
            double amount = Double.parseDouble(args[3]);
            return new Command(from, to, amount);
        }
        throw new IllegalArgumentException(
                "Invalid command. Format: "  +
                        " --oneline <FROM> <TO> <AMOUNT>"
        );
    }
}
