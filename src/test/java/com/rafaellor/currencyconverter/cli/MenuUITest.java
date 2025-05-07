package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MenuUITest {

    @Test
    void start_onExit_displaysMenuAndGoodbye() throws Exception {
        // Arrange
        CurrencyConverter converter = mock(CurrencyConverter.class);
        ExchangeRateService client = mock(ExchangeRateService.class);
        ConversionHistoryManager historyMgr = mock(ConversionHistoryManager.class);
        ResourceBundle messages = mock(ResourceBundle.class);

        // Stub the keys used in printMenu() and exit
        when(messages.getString("menu.title")).thenReturn("Main Menu");
        when(messages.getString("menu.favorites")).thenReturn("Favorites");
        when(messages.getString("menu.convert")).thenReturn("Convert");
        when(messages.getString("menu.recentlyConverted")).thenReturn("History");
        when(messages.getString("menu.list")).thenReturn("List Rates");
        when(messages.getString("menu.settings")).thenReturn("Settings");
        when(messages.getString("menu.exit")).thenReturn("Exit");
        when(messages.getString("menu.prompt")).thenReturn("Choice:");
        when(messages.getString("goodbye")).thenReturn("Goodbye!");

        // Act: inject "0\n" to exit immediately
        String out = tapSystemOut(() ->
                withTextFromSystemIn("0\n")
                        .execute(() ->
                                new MenuUI(converter, client, messages, historyMgr).start()
                        )
        );

        // Assert
        assertTrue(out.contains("==== Main Menu ===="), "Should show menu title");
        assertTrue(out.contains("1) Favorites"),    "Should list Favorites");
        assertTrue(out.contains("2) Convert"),      "Should list Convert");
        assertTrue(out.contains("3) History"),      "Should list History");
        assertTrue(out.contains("4) List Rates"),   "Should list List Rates");
        assertTrue(out.contains("5) Settings"),     "Should list Settings");
        assertTrue(out.contains("0) Exit"),         "Should list Exit");
        assertTrue(out.contains("Goodbye!"),        "Should say goodbye on exit");
    }
}
