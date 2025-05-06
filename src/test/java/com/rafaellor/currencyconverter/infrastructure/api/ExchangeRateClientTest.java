package com.rafaellor.currencyconverter.infrastructure.api;

import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateClientTest {

    @Test
    void testGetExchangeRateReturnsValueGreaterThanZero() throws Exception {

        ConfigLoader config = new ConfigLoader();
        ExchangeRateClient client = new ExchangeRateClient(config);

        double rate = client.getExchangeRate("USD", "BRL");

        assertTrue(rate > 0, "A taxa de câmbio deve ser maior que zero.");
    }
}
