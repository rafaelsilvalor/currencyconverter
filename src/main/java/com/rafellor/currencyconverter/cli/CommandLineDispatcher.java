package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;

public class CommandLineDispatcher {
    private final ConfigLoader config = new ConfigLoader();
    private final ExchangeRateService service = new ExchangeRateClient(config);
    private final CurrencyConverter converter = new CurrencyConverter(service);

    public void handle(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("cc") && args[1].equalsIgnoreCase("--list")) {
            handleList();
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cc") == args[1].equalsIgnoreCase("--menu"))  {
            new MenuUI(converter, service).start();

        } else if (args.length == 4 && args[0].equalsIgnoreCase("cc")) {
            handleCommand(args);
        } else {
            new InteractiveConsoleUI(converter).start();
        }
    }


    private void handleList() {
        System.out.println("== Supported Currencies ==\n");
        ExchangeRateClient client = new ExchangeRateClient(new ConfigLoader());
        client.getSupportedCodes().forEach(System.out::println);
    }

    private void handleCommand(String[] args) {
        try {
            var command = CommandLineParser.parse(args);
            double result = converter.convert(command.amount(), command.from(), command.to());
            System.out.printf("== %.2f %s == %.2f %s%n",
                    command.amount(), command.from(), result, command.to());
        } catch (Exception e) {
            System.out.println("!! CLI Error: " + e.getMessage());
        }
    }
}
