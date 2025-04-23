package com.rafellor.currencyconverter.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CommandLineParserTest {

    @Test
    void shouldParseValidCurrencyCommand(){
        String[] args = {"cc", "usd", "brl", "50.0"};
        CommandLineParser.Command command = CommandLineParser.parse(args);


        assertEquals("USD", command.from());
        assertEquals("BRL", command.to());
        assertEquals(50.0, command.amount(), 0.001);
    }

    @Test
    void shouldThrowOnInvalidCommandFormat() {
        String[] args = {"cc", "usd", "brl"};
        assertThrows(IllegalArgumentException.class, () -> CommandLineParser.parse(args));
    }
}
