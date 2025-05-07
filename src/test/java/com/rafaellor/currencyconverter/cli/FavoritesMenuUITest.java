// src/test/java/com/rafaellor/currencyconverter/cli/FavoritesMenuUITest.java
package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.domain.Favorite;
import com.rafaellor.currencyconverter.domain.ExchangeRateService;
import com.rafaellor.currencyconverter.infrastructure.favorites.FavoritesManager;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class FavoritesMenuUITest {

    @Test
    void start_withFavorites_displaysListAndBackOption() throws Exception {
        // Arrange
        FavoritesManager favMgr = mock(FavoritesManager.class);
        List<Favorite> favorites = List.of(
                new Favorite("USD","BRL"),
                new Favorite("EUR","JPY")
        );
        when(favMgr.getFavorites()).thenReturn(favorites);

        ResourceBundle msgs = mock(ResourceBundle.class);
        when(msgs.getString("favorites.title")).thenReturn("My Favorites");
        when(msgs.getString("favorites.page")).thenReturn("Page {0}/{1}");
        when(msgs.getString("favorites.option.previous")).thenReturn("Previous");
        when(msgs.getString("favorites.option.next")).thenReturn("Next");
        when(msgs.getString("favorites.option.add")).thenReturn("Add");
        when(msgs.getString("favorites.option.remove")).thenReturn("Remove");
        when(msgs.getString("favorites.option.back")).thenReturn("Back");
        when(msgs.getString("menu.prompt")).thenReturn("Select:");

        CurrencyConverter converter = mock(CurrencyConverter.class);
        ExchangeRateService client = mock(ExchangeRateService.class);
        ConversionHistoryManager historyMgr = mock(ConversionHistoryManager.class);

        // Act: inject "0\n" to choose Back immediately
        String out = tapSystemOut(() ->
                withTextFromSystemIn("0\n").execute(() ->
                        new FavoritesMenuUI(
                                converter, client, favMgr, historyMgr, msgs
                        ).start()
                )
        );

        // Assert
        assertTrue(out.contains("==== My Favorites ===="),
                "Should print the favorites title");
        assertTrue(out.contains("1) USD->BRL"),
                "Should list the first favorite");
        assertTrue(out.contains("2) EUR->JPY"),
                "Should list the second favorite");
        assertTrue(out.contains("0) Back"),
                "Should list the Back option");
    }
}
