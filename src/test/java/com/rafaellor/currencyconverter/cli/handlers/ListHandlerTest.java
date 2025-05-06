// src/main/java/com/rafellor/currencyconverter/cli/handlers/ListHandler.java
//package com.rafaellor.currencyconverter.cli.handlers;
//
//import com.rafaellor.currencyconverter.cli.CliConfig;
//import com.rafaellor.currencyconverter.domain.ExchangeRateService;
//import com.rafaellor.currencyconverter.infrastructure.api.ExchangeRateClient;
//import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
//
//import java.util.ResourceBundle;
//
//public class ListHandler implements CommandHandler {
//    private final ConfigLoader config = new ConfigLoader();
//    private final ExchangeRateService service;
//    private final ResourceBundle messages;
//
//    /** Production ctor: wires up real service. */
//    public ListHandler(ResourceBundle messages) {
//        this(
//                new ExchangeRateClient(config),
//                messages
//        );
//    }
//
//    /** Test ctor: let tests supply a stub service. */
//    public ListHandler(ExchangeRateService service, ResourceBundle messages) {
//        this.service  = service;
//        this.messages = messages;
//    }
//
//    @Override
//    public boolean matches(String[] args) {
//        return args.length == 2
//                && args[0].equalsIgnoreCase(CliConfig.COMMAND)
//                && args[1].equalsIgnoreCase("--list");
//    }
//
//    @Override
//    public void execute(String[] args) {
//        System.out.println(messages.getString("favorites.title"));
//        service.getSupportedCodes().forEach(System.out::println);
//    }
//}
