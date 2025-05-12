package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class OneLineConversionTest {
    private OneLineConversion handler;

    @BeforeEach
    void setUp() {
        ResourceBundle messages = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][]{
                        {"error.cli", "Error:"}
                };
            }
        };
        handler = new OneLineConversion(messages);
    }

    @Test
    void testMatchesOnOneLine() {
        String[] args = {
                "--oneline",
                "USD",
                "EUR",
                "100"
        };
        assertTrue(handler.matches(args), "Should match --oneline plus 3 params");
    }
}
