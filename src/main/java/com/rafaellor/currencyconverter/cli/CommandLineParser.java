package com.rafellor.currencyconverter.cli;

public class CommandLineParser {

    public record Command(String from, String to, double amount) {}

    public static Command parse(String[] args) {
        if ( args.length == 5 && args[0].equalsIgnoreCase(CliConfig.COMMAND)
        && args[1].equalsIgnoreCase("--oneline")) {
            String from = args[2].toUpperCase();
            String to = args[3].toUpperCase();
            double amount = Double.parseDouble(args[4]);
            return new Command(from, to ,amount);
        }
        throw  new IllegalArgumentException("Invalid command. Format: cvc --oneline USD BRL 50.0");
    }
}
