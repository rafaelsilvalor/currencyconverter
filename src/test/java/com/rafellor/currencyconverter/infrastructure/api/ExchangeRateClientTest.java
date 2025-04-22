package com.rafellor.currencyconverter.infrastructure.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateClientTest {

    @Test
    void testGetExchangeRateReturnsValueGreaterThanZero() throws Exception {
        ExchangeRateClient client = new ExchangeRateClient();
        double rate = client.getExchangeRate("USD", "BRL");

        assertTrue(rate > 0, "A taxa de câmbio deve ser maior que zero.");
    }
}
