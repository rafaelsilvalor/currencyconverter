package com.rafellor.currencyconverter.domain;

public interface ExchangeRateService {
    double getExchangeRate(String fromCurrency, String toCorrency);
}
