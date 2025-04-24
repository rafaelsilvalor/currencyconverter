package com.rafellor.currencyconverter.application;

import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.api.ExchangeRateClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CurrencyConverterTest {

    @Test
    void testConvertShouldMultiplyByRate() {
        ExchangeRateService mockService = Mockito.mock(ExchangeRateClient.class);
        when(mockService.getExchangeRate("USD", "BRL")).thenReturn(5.0);

        CurrencyConverter converter = new CurrencyConverter(mockService);
        double result = converter.convert(10.0, "USD", "BRL");
        assertEquals(50.0, result);
    }
}
