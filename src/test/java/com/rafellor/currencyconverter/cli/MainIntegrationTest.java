package com.rafellor.currencyconverter.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainIntegrationTest {
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
    void testCliConversionFlow() {
        // Simulate user input
        String simulatedInput = "10\nUSD\nBRL\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run Main in interactive mode
        Main.main(new String[]{});

        String output = outContent.toString().toUpperCase();
        output = output.replace(',', '.'); // normalize commas to dots

        // Validate prompts
        assertTrue(output.contains("AMOUNT:"), "Missing amount prompt");
        assertTrue(output.contains("FROM CURRENCY"), "Missing from currency prompt");
        assertTrue(output.contains("TO CURRENCY"), "Missing to currency prompt");

        // Validate final conversion line
        assertTrue(
                output.matches("(?s).*==\\s*\\d+\\.\\d+\\s+[A-Z]{3}\\s*==\\s*\\d+\\.\\d+\\s+[A-Z]{3}.*"),
                "Expected conversion result format not found"
        );
    }

}
