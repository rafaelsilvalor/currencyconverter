package com.rafaellor.currencyconverter.cli.handlers;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuHandlerTest {

    @Test
    void matches_withOnlyCommand_returnsTrue() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        assertTrue(handler.matches(new String[]{}),
                "Handler should open without args");
    }

    @Test
    void matches_withInvalidArgs_returnsFalse() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        assertFalse(handler.matches(new String[]{"foo"}),
                "Handler should not match unknown commands");
    }
}
