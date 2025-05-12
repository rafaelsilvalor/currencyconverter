package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class InteractiveConversionTest {
    private InteractiveConversion handler;

    @BeforeEach
    public void setUp() {
        ResourceBundle messages = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                        { "prompt.from",  "From:" },
                        { "prompt.to",    "To:"   },
                        { "error.cli",    "Error:" }
                };
            }
        };
        handler = new InteractiveConversion(messages);
    }

    @Test
    public void testMatchesOnInteractive() {
        String[] args = { "--interactive" };
        assertTrue(handler.matches(args), "Should match --interactive flag");
    }
}
