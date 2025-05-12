package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class ListHandlerTest {
    private ListHandler handler;

    @BeforeEach
    void setUp() {
        ResourceBundle messages = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][]{{"menu.list", "Supported currencies:"}, {"menu.empty", "No currencies available."}};
            }
        };
        handler = new ListHandler(messages);
    }

    @Test
    @DisplayName("matches() returns true on 'cvc --list'")
    void testMatchesListFlag() {
        String[] args = { "--list" };
        assertTrue(handler.matches(args));
    }

    @Test
    @DisplayName("execute() prints all supported codes")
    void testExecutePrintsSupportedCodes() {
        // TODO: stub service.getSupportedCodes() to return e.g. ["USD","EUR"],
        // capture System.out, call handler.execute(new String[]{CliConfig.COMMAND, "--list"}),
        // and assert that header + each code is printed.
    }
}
