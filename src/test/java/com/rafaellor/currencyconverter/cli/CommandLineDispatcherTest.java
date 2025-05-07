package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.cli.handlers.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class CommandLineDispatcherTest {

    private CommandLineDispatcher dispatcher;
    private CommandHandler mockHandler;
    private ResourceBundle messages;

    @BeforeEach
    void setUp() throws Exception {
        // Create a mock ResourceBundle
        messages = Mockito.mock(ResourceBundle.class);
        dispatcher = new CommandLineDispatcher(messages);

        // Inject mock handler into the private handlers list
        Field handlersField = CommandLineDispatcher.class.getDeclaredField("handlers");
        handlersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<CommandHandler> handlers = (List<CommandHandler>) handlersField.get(dispatcher);

        mockHandler = Mockito.mock(CommandHandler.class);
        handlers.add(mockHandler);
    }

    @Test
    void handle_matchingHandler_executes() {
        String[] args = {"convert", "USD", "BRL", "100"};
        Mockito.when(mockHandler.matches(args)).thenReturn(true);

        dispatcher.handle(args);

        verify(mockHandler).execute(args);
    }

    @Test
    void handle_nonMatching_showsError() throws Exception {
        String[] args = {"unknown"};
        Mockito.when(mockHandler.matches(args)).thenReturn(false);
        Mockito.when(messages.getString("error.invalid.commandline")).thenReturn("Invalid command");

        String output = tapSystemOut(() -> dispatcher.handle(args));

        assertTrue(output.contains("Invalid command"));
    }
}
