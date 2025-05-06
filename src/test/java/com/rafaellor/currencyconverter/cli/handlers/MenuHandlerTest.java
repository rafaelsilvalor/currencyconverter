package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.cli.CliConfig;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuHandlerTest {

    /**
     * A single‑argument command equal to CliConfig.COMMAND should match.
     */
    @Test
    void matches_withOnlyCommand_returnsTrue() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        String cmd = CliConfig.COMMAND;
        assertTrue(handler.matches(new String[]{cmd}),
                "Handler should match when args = [COMMAND]");
    }

    /**
     * Any other argument patterns should not match.
     */
    @Test
    void matches_withInvalidArgs_returnsFalse() {
        ResourceBundle messages = mock(ResourceBundle.class);
        MenuHandler handler = new MenuHandler(messages);

        // Too many args
        assertFalse(handler.matches(new String[]{CliConfig.COMMAND, "extra"}),
                "Handler should not match when there are extra args");

        // Different command
        assertFalse(handler.matches(new String[]{"foo"}),
                "Handler should not match unknown commands");
    }
}
