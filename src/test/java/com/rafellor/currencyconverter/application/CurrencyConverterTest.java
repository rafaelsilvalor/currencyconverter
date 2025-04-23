package com.rafellor.currencyconverter.application;

import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {

    @Test
    void testConvertShouldMultiplyByRate() {
        ExchangeRateService mockService = (from, to) -> 5.0;
        CurrencyConverter converter = new CurrencyConverter(mockService);

        double result = converter.convert(10.0, "USD", "BRL");
        assertEquals(50.0, result);
    }
}
