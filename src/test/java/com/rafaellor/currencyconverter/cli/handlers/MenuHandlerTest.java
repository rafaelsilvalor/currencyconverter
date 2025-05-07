package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.cli.CliConfig;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuHandlerTest {

    @Test
    void matches_withOnlyCommand_returnsTrue() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        String cmd = CliConfig.COMMAND;
        assertTrue(handler.matches(new String[]{cmd}),
                "Handler should match when args = [COMMAND]");
    }

    @Test
    void matches_withInvalidArgs_returnsFalse() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        assertFalse(handler.matches(new String[]{CliConfig.COMMAND, "extra"}),
                "Handler should not match when there are extra args");
        assertFalse(handler.matches(new String[]{"foo"}),
                "Handler should not match unknown commands");
    }
}
