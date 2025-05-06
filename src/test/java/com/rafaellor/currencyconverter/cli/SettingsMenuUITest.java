package com.rafaellor.currencyconverter.cli;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SettingsMenuUITest {

    /**
     * When the user enters "0", start() should display the settings menu once and then return.
     */
    @Test
    void start_onExitOption_displaysMenuAndReturns() throws Exception {
        // Prepare mocks
        ResourceBundle messages = mock(ResourceBundle.class);
        when(messages.getString("settings.title")).thenReturn("Settings");
        when(messages.getString("settings.language")).thenReturn("Language");
        when(messages.getString("settings.back")).thenReturn("Back");
        when(messages.getString("menu.prompt")).thenReturn("Choose:");

        Properties settings = new Properties();

        // Capture stdout while injecting "0\n" into System.in **before** creating the UI
        String output = tapSystemOut(() ->
                withTextFromSystemIn("0\n")
                        .execute(() -> {
                            // Instantiate inside the withTextFromSystemIn so Scanner wraps the test input
                            SettingsMenuUI ui = new SettingsMenuUI(settings, messages);
                            ui.start();
                        })
        );

        // Verify header, options and prompt appeared
        assertTrue(output.contains("==== Settings ===="), "Should print the menu title");
        assertTrue(output.contains("1) Language"),      "Should list the language option");
        assertTrue(output.contains("0) Back"),          "Should list the back/exit option");
        assertTrue(output.contains("Choose:"),          "Should prompt the user");
    }
}
