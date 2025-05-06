package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.cli.CliConfig;
import com.rafaellor.currencyconverter.cli.CommandLineParser;
import com.rafaellor.currencyconverter.cli.util.ConversionHandler;
import com.rafaellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class OneLineConversion implements CommandHandler {
    private final ResourceBundle messages;
    private final CurrencyConverter converter;
    private final ConversionHistoryManager historyManager;

    public OneLineConversion(ResourceBundle messages) {
        this.messages = messages;
        ConfigLoader config = new ConfigLoader();
        this.converter = new CurrencyConverter(new ExchangeRateClient(config));
        this.historyManager = new ConversionHistoryManager(Paths.get("conversion-history.txt"));
    }

    @Override
    public boolean matches(String[] args) {
        return args.length == 5
                && args[0].equalsIgnoreCase(CliConfig.COMMAND)
                && args[1].equalsIgnoreCase("--oneline");
    }

    @Override
    public void execute(String[] args) {
        try {
            var command = CommandLineParser.parse(args);
            ConversionHandler.perform(
                    command.amount(), command.from(), command.to(),
                    converter, historyManager, messages
            );
        } catch (Exception e) {
            System.out.println(
                    MessageFormat.format(messages.getString("error.cli"), e.getMessage())
            );
        }
    }
}
