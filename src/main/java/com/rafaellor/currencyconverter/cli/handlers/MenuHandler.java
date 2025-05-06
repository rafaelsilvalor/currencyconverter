package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.cli.CliConfig;
import com.rafaellor.currencyconverter.cli.MenuUI;
import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MenuHandler implements CommandHandler {

    private final ResourceBundle messages;
    private final CurrencyConverter converter;
    private final ExchangeRateService service;
    private final ConversionHistoryManager historyManager;

    /**
     * Production constructor: only needs the messages bundle.
     * It will create its own service, converter and history manager.
     */
    public MenuHandler(ResourceBundle messages) {
        this.messages = messages;

        ConfigLoader config = new ConfigLoader();
        this.service = new ExchangeRateClient(config);
        this.converter = new CurrencyConverter(service);

        // uses the same history file path as Main
        this.historyManager = new ConversionHistoryManager(Paths.get("conversion-history.txt"));
    }

    @Override
    public boolean matches(String[] args) {
        // cc (or cvc) with no flags
        return args.length == 1
                && args[0].equalsIgnoreCase(CliConfig.COMMAND);
    }

    @Override
    public void execute(String[] args) {
        new MenuUI(converter, service, messages, historyManager).start();
    }
}
