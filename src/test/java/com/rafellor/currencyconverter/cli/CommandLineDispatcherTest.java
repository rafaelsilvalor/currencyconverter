package com.rafellor.currencyconverter.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandLineDispatcherTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintSupportedCurrenciesWhenListFlagIsUsed() {
        CommandLineDispatcher dispatcher = new CommandLineDispatcher();
        dispatcher.handle(new String[] {"--list"});

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("supported currencies"));
        assertTrue(output.contains("usd")); // assuming USD is always in the list
    }

    @Test
    void shouldTriggerCliConversionForValidArgs() {
        CommandLineDispatcher dispatcher = new CommandLineDispatcher();
        dispatcher.handle(new String[] {"cc", "usd", "brl", "10"});

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("usd") && output.contains("brl"));
        assertTrue(output.contains("=="));
    }
}
