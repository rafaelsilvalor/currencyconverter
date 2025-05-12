package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
import java.util.ResourceBundle;



public class ListHandler implements CommandHandler {
    private final ConfigLoader config = new ConfigLoader();
    private final ExchangeRateService service = new ExchangeRateClient(config);
    private final ResourceBundle messages;

    public ListHandler(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public boolean matches(String[] args) {
        return args.length == 1
                && args[0].equalsIgnoreCase("--list");
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\n====== " + messages.getString("menu.list") + " ======\n");
        service.getSupportedCodes().forEach(System.out::println);

    }

}

