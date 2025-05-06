package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;

public class ConsoleRunner {

    private final CurrencyConverter converter;

    public ConsoleRunner(CurrencyConverter converter) {
        this.converter = converter;
    }

    public double convert(String from, String to, double amount) {
        if (from.length() != 3 || to.length() != 3) {
            throw new IllegalArgumentException("Currency codes must be 3 letters.");
        }

        return converter.convert(amount, from.toUpperCase(), to.toUpperCase());
    }
}
