package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
        // Simulated input: amount, from, to
        String simulatedInput = "10\nUSD\nBRL\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.ENGLISH);

        ExchangeRateService mockService = new ExchangeRateService() {
            public double getExchangeRate(String from, String to) {
                return 5.0;
            }

            public List<String> getSupportedCodes() {
                return List.of("USD", "BRL");
            }
        };
        CurrencyConverter converter = new CurrencyConverter(mockService);

        InteractiveConsoleUI ui = new InteractiveConsoleUI(converter, messages);
        ui.start();

        String output = outContent.toString();
        System.out.println("Captured output:\n" + output); // For debug

        assertTrue(output.contains(messages.getString("prompt.amount")));
        assertTrue(output.contains(messages.getString("prompt.from")));
        assertTrue(output.contains(messages.getString("prompt.to")));
        assertTrue(output.contains("=="));
    }
}
