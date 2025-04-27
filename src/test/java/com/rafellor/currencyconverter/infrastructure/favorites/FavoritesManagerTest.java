package com.rafellor.currencyconverter.infrastructure.favorites;

import com.rafellor.currencyconverter.domain.Favorite;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoritesManagerTest {

    private static final String TEST_FILE = "test-favorites.properties";
    private FavoritesManager manager;

    @BeforeEach
    void setUp() {
        new File(TEST_FILE).delete(); // clean slate
        manager = new FavoritesManager(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    void shouldAddAndRetrieveFavorites() {
        Favorite fav = new Favorite("USD", "BRL");
        manager.addFavorite(fav);

        List<Favorite> favorites = manager.getFavorites();
        assertEquals(1, favorites.size());
        assertTrue(favorites.contains(fav));
    }

    @Test
    void shouldRemoveFavorite() {
        Favorite fav = new Favorite("EUR", "JPY");
        manager.addFavorite(fav);
        manager.removeFavorite(fav);

        List<Favorite> favorites = manager.getFavorites();
        assertFalse(favorites.contains(fav));
    }

    @Test
    void shouldPersistAndReloadFavorites() {
        Favorite fav = new Favorite("BRL", "USD");
        manager.addFavorite(fav);

        FavoritesManager reloaded = new FavoritesManager(TEST_FILE);
        assertTrue(reloaded.getFavorites().contains(fav));
    }
}