package com.rafellor.currencyconverter.infrastructure.favorites;

import com.rafellor.currencyconverter.domain.Favorite;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FavoritesManager {
    private final Properties properties = new Properties();
    private final File file;

    public FavoritesManager(String filename) {
        this.file = new File(filename);
        loadFavorites();
    }

    private void loadFavorites() {
        if (file.exists()) {
            try (InputStream input = new FileInputStream(file)) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load favorites", e);
            }
        }
    }

    public List<Favorite> getFavorites() {
        return properties.keySet().stream()
                .map(Object::toString)
                .map(this::keyToFavorite)
                .collect(Collectors.toList());
    }

    public void addFavorite(Favorite favorite) {
        properties.setProperty(favorite.key(), "1");
        saveFavorites();
    }

    public void removeFavorite(Favorite favorite) {
        properties.remove(favorite.key());
        saveFavorites();
    }

    public void saveFavorites() {
        try (OutputStream output = new FileOutputStream(file)) {
            properties.store(output, "Favorites List");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save favorites", e);
        }
    }

    private Favorite keyToFavorite(String key) {
        String[] parts = key.split("->");
        return new Favorite(parts[0], parts[1]);
    }
}
