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
    void setUpStream(){
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
        // simulate user input: amount, from, to
        String simulatedInput = "10\nUSD\nBRL\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));


        // Run main ClI
        Main.main(new String[]{});

        String output = outContent.toString();

        // Check that prompts and result were printed
        assertTrue(output.contains("Enter amount"));
    }
}
