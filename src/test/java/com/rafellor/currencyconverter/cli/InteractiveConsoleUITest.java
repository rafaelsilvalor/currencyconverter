package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InteractiveConsoleUITest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void shouldHandleInteractiveFlowSuccessfully() {
        // simulate user input: amount, from, to
        String simulatedInput = "10\nUSD\nBRL\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // use a simple mock of ExchangeRateService
        ExchangeRateService mockService = (from, to) -> 5.0;
        CurrencyConverter converter = new CurrencyConverter(mockService);

        InteractiveConsoleUI ui = new InteractiveConsoleUI(converter);
        ui.start();

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("amount"));
        assertTrue(output.contains("from currency"));
        assertTrue(output.contains("to currency"));
        assertTrue(output.contains("=="));
    }
}
