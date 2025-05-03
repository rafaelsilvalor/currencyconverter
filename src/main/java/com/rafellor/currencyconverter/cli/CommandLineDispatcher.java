package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;

import java.util.ResourceBundle;

public class CommandLineDispatcher {
    private final ResourceBundle messages;
    private final ConfigLoader config = new ConfigLoader();
    private final ExchangeRateService service = new ExchangeRateClient(config);
    private final CurrencyConverter converter = new CurrencyConverter(service);

    public CommandLineDispatcher(ResourceBundle messages) {
        this.messages = messages;
    }

    public void handle(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("cc") && args[1].equalsIgnoreCase("--list")) {
            handleList();
        } else if (args.length == 4 && args[0].equalsIgnoreCase("cc")) {
            handleCommand(args);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("cc")) {
            new InteractiveConsoleUI(converter, messages).start();
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cc") && args[1].equalsIgnoreCase("--menu")) {
            MenuUI menuUI = new MenuUI(converter, service, messages);
            menuUI.start();
        } else {
            System.out.println(messages.getString("error.invalid.commandline"));
        }
    }

    private void handleList() {
        System.out.println(messages.getString("favorites.title"));
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
            System.out.println(messages.getString("error.cli") + ": " + e.getMessage());
        }
    }
}
