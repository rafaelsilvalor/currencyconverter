package com.rafellor.currencyconverter.infrastructure.favorites;

import com.rafellor.currencyconverter.domain.Favorite;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoritesManagerTest {

    private static final String TEST_FILE = "test-favorites.properties";
    private FavoritesManager manager;

    @BeforeEach
    void setUp() {
        manager = new FavoritesManager(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void shouldStartWithEmptyFavoritesWhenFileDoesNotExist() {
        List<Favorite> favorites = manager.getFavorites();
        assertTrue(favorites.isEmpty(), "Favorites should start empty if no file exists");
    }

    @Test
    void shouldAddFavoriteAndPersist() {
        Favorite favorite = new Favorite("USD", "BRL");

        manager.addFavorite(favorite);

        List<Favorite> favorites = manager.getFavorites();
        assertEquals(1, favorites.size());
        assertEquals("USD", favorites.get(0).from());
        assertEquals("BRL", favorites.get(0).to());
    }

    @Test
    void shouldLoadFavoritesFromFile() throws IOException {
        // Manually create a properties file
        File file = new File(TEST_FILE);
        try (var writer = new java.io.FileWriter(file)) {
            writer.write("0=USD->BRL\n");
            writer.write("1=EUR->JPY\n");
        }

        FavoritesManager loadedManager = new FavoritesManager(TEST_FILE);
        List<Favorite> favorites = loadedManager.getFavorites();

        assertEquals(2, favorites.size());
        assertEquals(new Favorite("USD", "BRL"), favorites.get(0));
        assertEquals(new Favorite("EUR", "JPY"), favorites.get(1));
    }

    @Test
    void shouldRemoveFavorite() {
        Favorite favorite = new Favorite("USD", "BRL");
        manager.addFavorite(favorite);

        manager.removeFavorite(favorite);

        List<Favorite> favorites = manager.getFavorites();
        assertTrue(favorites.isEmpty(), "Favorites should be empty after removal");
    }

    @Test
    void shouldHandleDuplicateFavorites() {
        Favorite favorite = new Favorite("USD", "BRL");

        manager.addFavorite(favorite);
        manager.addFavorite(favorite); // Try to add again

        List<Favorite> favorites = manager.getFavorites();
        assertEquals(1, favorites.size(), "Should not allow duplicate favorites");
    }

    @Test
    void shouldHandleMalformedPropertiesGracefully() throws IOException {
        File file = new File(TEST_FILE);
        try (var writer = new java.io.FileWriter(file)) {
            writer.write("ThisIsNotAValidFavoriteFormat\n");
        }

        FavoritesManager loadedManager = new FavoritesManager(TEST_FILE);
        List<Favorite> favorites = loadedManager.getFavorites();

        assertTrue(favorites.isEmpty(), "Malformed properties should not crash the app");
    }
}
