// src/test/java/com/rafaellor/currencyconverter/cli/handlers/StubExchangeRateServiceTest.java
package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StubExchangeRateServiceTest {
    private StubExchangeRateService stub;

    @BeforeEach
    public void setUp() {
        stub = new StubExchangeRateService(List.of("USD", "EUR", "BRL"));
    }

    @Test
    public void testGetSupportedCodes() {
        List<String> codes = stub.getSupportedCodes();
        assertNotNull(codes, "getSupportedCodes() should never return null");
        assertTrue(codes.isEmpty(), "Default stub returns empty list");
    }

    @Test
    public void testGetExchangeRateThrows() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> stub.getExchangeRate("USD", "EUR"),
                "StubExchangeRateService.getExchangeRate(...) should always throw"
        );
    }
}
