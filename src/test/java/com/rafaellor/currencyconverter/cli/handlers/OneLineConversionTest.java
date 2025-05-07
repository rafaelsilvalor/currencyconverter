package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.cli.CliConfig;
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
                CliConfig.COMMAND,
                "--oneline",
                "100",
                "USD",
                "EUR"
        };
        assertTrue(handler.matches(args), "Should match --oneline plus 3 params");
    }
}
