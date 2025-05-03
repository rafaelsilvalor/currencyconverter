package com.rafellor.currencyconverter.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandLineDispatcherTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ResourceBundle messages;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        messages = ResourceBundle.getBundle("messages", Locale.ENGLISH);
    }

    @Test
    void shouldListSupportedCurrencies() {
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages);
        dispatcher.handle(new String[]{"cc", "--list"});

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("usd") || output.contains("eur") || output.contains("brl"));
    }

    @Test
    void shouldPrintErrorForInvalidCommand() {
        CommandLineDispatcher dispatcher = new CommandLineDispatcher(messages);
        dispatcher.handle(new String[]{"cc", "--wrong"});

        String output = outContent.toString();
        assertTrue(output.contains(messages.getString("error.invalid.commandline")));
    }
}
