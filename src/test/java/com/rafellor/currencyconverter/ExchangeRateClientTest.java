package com.rafellor.currencyconverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateClientTest {

    @Test
    void testWelcomeMessage() {
        ExchangeRateClient client = new ExchangeRateClient();
        assertEquals("Currency Converter is ready!", client.getWelcomeMessage());
    }
}
