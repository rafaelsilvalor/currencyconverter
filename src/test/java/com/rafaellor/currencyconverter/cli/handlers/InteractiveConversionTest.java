// src/test/java/com/rafaellor/currencyconverter/cli/handlers/InteractiveConversionTest.java
package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.cli.CliConfig;
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
        String[] args = { CliConfig.COMMAND, "--interactive" };
        assertTrue(handler.matches(args), "Should match --interactive flag");
    }
}
