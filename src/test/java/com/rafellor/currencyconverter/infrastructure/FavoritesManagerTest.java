package com.rafellor.currencyconverter.infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoritesManagerTest {
    private static final String TEST_FILE = "test-favorites.properties";
    private FavoritesManager manager;

    @BeforeEach
    void setUp() {
        new File(TEST_FILE).delete();
        manager = new FavoritesManager(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    void shouldAddReatrieveFavorites() {
        Favorite fav = new Favorite("USD", "BRL");
        manager.addFavorite(fav);

        List<Favorite> favorites = manager.getFavorites();
        assertEquals(1, favorites.size());
        assertTrue(favorites.contains(fav));
    }

    @Test
    void shouldRemoveFavorites() {
       Favorite fav = new Favorite("BRL", "USD");
       manager.addFavorite(fav);

       FavoritesManager reloaded = new FavoritesManager(TEST_FILE);
       assertTrue(realoaded.getFavorites().contains(fav));
    }
}
