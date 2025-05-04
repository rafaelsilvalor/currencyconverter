package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.cli.util.HistoryUtils;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;
import com.rafellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class CommandLineDispatcher {
    private final ResourceBundle messages;
    private final ConfigLoader config = new ConfigLoader();
    private final ExchangeRateService service = new ExchangeRateClient(config);
    private final CurrencyConverter converter = new CurrencyConverter(service);
    private final ConversionHistoryManager historyManager;

    public CommandLineDispatcher(ResourceBundle messages, ConversionHistoryManager historyManager) {
        this.messages = messages;
        this.historyManager = historyManager;
    }

    public void handle(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("cc") && args[1].equalsIgnoreCase("--list")) {
            handleList();
        } else if (args.length == 4 && args[0].equalsIgnoreCase("cc")) {
            handleCommand(args);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("cc")) {
            new InteractiveConsoleUI(converter, messages).start();
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cc") && args[1].equalsIgnoreCase("--menu")) {
            MenuUI menuUI = new MenuUI(converter, service, messages, historyManager);
            menuUI.start();
        } else {
            System.out.println(messages.getString("error.invalid.commandline"));
        }
    }

    private void handleList() {
        System.out.println(messages.getString("favorites.title"));
        service.getSupportedCodes().forEach(System.out::println);
    }

    private void handleCommand(String[] args) {
        try {
            // instead of stripping "cc", just give them all back to the parser:
            var command = CommandLineParser.parse(args);

            double result = converter.convert(
                    command.amount(),
                    command.from(),
                    command.to()
            );
            System.out.printf("== %.2f %s == %.2f %s%n",
                    command.amount(), command.from(),
                    result, command.to());

            // save history as before...
            HistoryUtils.recordHistory(
                    historyManager,
                    command.from(),
                    command.to(),
                    command.amount(),
                    result,
                    messages
            );

        } catch (Exception e) {
            System.out.println(
                    MessageFormat.format(messages.getString("error.cli"), e.getMessage())
            );
        }
    }
}