package com.rafellor.currencyconverter.domain;

import java.util.List;

public interface ExchangeRateService {
    double getExchangeRate(String fromCurrency, String toCorrency);
    List<String> getSupportedCodes();
}
