package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleRunnerTest {

    @Test
    void shouldConvertSuccessfullyWithValidCodes() {
        ExchangeRateService mock = (from, to) -> 4.0;
        CurrencyConverter converter = new CurrencyConverter(mock);
        ConsoleRunner runner = new ConsoleRunner(converter);

        double result = runner.convert("usd", "brl", 10.0);
        assertEquals(40.0, result);
    }

    @Test
    void shouldThrowExceptionForInvalidCurrencyCode() {
        ExchangeRateService mock = (from, to) -> 1.0;
        CurrencyConverter converter = new CurrencyConverter(mock);
        ConsoleRunner runner = new ConsoleRunner(converter);

        assertThrows(IllegalArgumentException.class, () -> runner.convert("USDD", "EUR", 10.0));
    }
}
