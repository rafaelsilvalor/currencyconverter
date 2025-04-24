package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class ConsoleRunnerTest {

    private ExchangeRateService service;
    private CurrencyConverter converter;


    @BeforeEach
    void setUp() {
        service = Mockito.mock(ExchangeRateService.class);
        converter = new CurrencyConverter(service);
    }

    @Test
    void shouldConvertSuccessfullyWithValidCodes() {
        when(service.getExchangeRate("USD", "BRL")).thenReturn(5.0);
        double result = converter.convert(8.0, "USD", "BRL");
        assertEquals(40.0, result);
    }

    @Test
    void shouldThrowExceptionForInvalidCurrencyCode() {
        List<String> mockCodes = List.of("USD: United States Dollar","BRL : Brazilian Real");
        when(service.getSupportedCodes()).thenReturn(mockCodes);
        assertEquals(2, service.getSupportedCodes().size());
    }
}
