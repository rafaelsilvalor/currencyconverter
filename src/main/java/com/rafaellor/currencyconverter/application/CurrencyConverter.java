package com.rafellor.currencyconverter.application;

import com.rafellor.currencyconverter.domain.ExchangeRateService;

public class CurrencyConverter {
    private final ExchangeRateService service;

    public CurrencyConverter(ExchangeRateService service) {
        this.service = service;
    }

    public double convert(double amount, String fromCurrency, String toCurrency) {
        double rate = service.getExchangeRate(fromCurrency, toCurrency);
        return amount * rate;
    }
}
