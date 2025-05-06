package com.rafaellor.currencyconverter.infrastructure.favorites;

import com.rafaellor.currencyconverter.domain.Favorite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FavoritesManager {
    private final String filename;
    private final Properties properties = new Properties();

    public FavoritesManager(String filename) {
        this.filename = filename;
        load();
    }

    private void load() {
        File file = new File(filename);
        if (file.exists()) {
            try (var input = new FileInputStream(file)) {
                properties.load(input);
            } catch (IOException e) {
                System.out.println("Warning: Failed to load favorites file: " + e.getMessage());
            }
        }
    }

    private void save() {
        try (var output = new FileOutputStream(filename)) {
            properties.store(output, null);
        } catch (IOException e) {
            System.out.println("Warning: Failed to save favorites file: " + e.getMessage());
        }
    }

    public List<Favorite> getFavorites() {
        List<Favorite> favorites = new ArrayList<>();
        for (var value : properties.values()) {
            try {
                favorites.add(keyToFavorite((String) value));
            } catch (IllegalArgumentException e) {
                System.out.println("Warning: Skipping invalid favorite: " + value);
            }
        }
        return favorites;
    }

    public void addFavorite(Favorite favorite) {
        if (getFavorites().contains(favorite)) {
            return; // Already exists, do not add
        }
        properties.setProperty(String.valueOf(properties.size()), favorite.from() + "->" + favorite.to());
        save();
    }

    public void removeFavorite(Favorite favorite) {
        var keysToRemove = new ArrayList<String>();

        for (var entry : properties.entrySet()) {
            if (favorite.equals(keyToFavorite((String) entry.getValue()))) {
                keysToRemove.add((String) entry.getKey());
            }
        }

        for (String key : keysToRemove) {
            properties.remove(key);
        }

        // Reindex properties
        Properties reindexed = new Properties();
        int index = 0;
        for (var value : properties.values()) {
            reindexed.setProperty(String.valueOf(index++), (String) value);
        }

        properties.clear();
        properties.putAll(reindexed);
        save();
    }

    private Favorite keyToFavorite(String value) {
        String[] parts = value.split("->");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid favorite format: " + value);
        }
        return new Favorite(parts[0], parts[1]);
    }
}
