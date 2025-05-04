package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
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
        // give it any path (we’re not asserting on history here)
        ConversionHistoryManager historyManager =
                new ConversionHistoryManager(Paths.get("target/test-history.txt"));
        CommandLineDispatcher dispatcher =
                new CommandLineDispatcher(messages, historyManager);

        dispatcher.handle(new String[]{"cc", "--list"});

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("usd") || output.contains("eur") || output.contains("brl"));
    }

    @Test
    void shouldPrintErrorForInvalidCommand() {
        ConversionHistoryManager historyManager =
                new ConversionHistoryManager(Paths.get("target/test-history.txt"));
        CommandLineDispatcher dispatcher =
                new CommandLineDispatcher(messages, historyManager);

        dispatcher.handle(new String[]{"cc", "--wrong"});

        String output = outContent.toString();
        assertTrue(output.contains(messages.getString("error.invalid.commandline")));
    }

}
