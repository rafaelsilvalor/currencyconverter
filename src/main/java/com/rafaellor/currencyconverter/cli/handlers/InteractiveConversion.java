package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.cli.CliConfig;
import com.rafaellor.currencyconverter.cli.InteractiveConsoleUI;
import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;

import java.util.ResourceBundle;

public class InteractiveConversion implements CommandHandler {

    private final ResourceBundle messages;
    private final ConfigLoader config = new ConfigLoader();
    private final ExchangeRateService service = new ExchangeRateClient(config);
    private final CurrencyConverter converter = new CurrencyConverter(service);

    public InteractiveConversion(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public boolean matches(String[] args) {
        return args.length == 2
                && args[0].equalsIgnoreCase(CliConfig.COMMAND)
                && args[1].equalsIgnoreCase("--interactive");
    }

    @Override
    public void execute(String[] args) {
       new InteractiveConsoleUI(converter, messages).start();
    }

}
