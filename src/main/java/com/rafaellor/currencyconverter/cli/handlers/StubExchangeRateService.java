package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.domain.ExchangeRateService;

import java.util.List;

public class StubExchangeRateService implements ExchangeRateService {
    public final List<String> codes;

    public StubExchangeRateService(List<String> codes) {
        this.codes = codes;
    }


    @Override
    public List<String> getSupportedCodes() {
        return List.of();
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCorrency) {
        throw new UnsupportedOperationException("Not needed for ListHandler tests");
    }
}
