package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.domain.Favorite;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoritesMenuUITest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private FavoritesManager favoritesManager;

    @BeforeEach
    void setUpStreams() throws IOException {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        favoritesManager = new FavoritesManager("test-favorites.properties");
        clearFavoritesFile();
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        clearFavoritesFile();
    }

    private void clearFavoritesFile() {
        File file = new File("test-favorites.properties");
        if (file.exists()) file.delete();
    }

//    @Test
//    void shouldDisplayFavoritesAndHandleSelection() {
//        favoritesManager.addFavorite(new Favorite("USD", "BRL"));
//        favoritesManager.addFavorite(new Favorite("EUR", "JPY"));
//
//        String simulatedInput = "1\n1000\n0\n0\n";
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//
//        CurrencyConverter converter = new CurrencyConverter(new ExchangeRateService() {
//            public double getExchangeRate(String from, String to) { return 5.0; }
//            public List<String> getSupportedCodes() { return List.of("USD", "BRL", "EUR", "JPY"); }
//        });
//
//        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.ENGLISH);
//        new FavoritesMenuUI(converter, favoritesManager, messages).start();
//
//        String output = outContent.toString();
//        assertTrue(output.contains("USD"));
//        assertTrue(output.contains("BRL"));
//        assertTrue(output.contains("EUR"));
//        assertTrue(output.contains("JPY"));
//    }
}
