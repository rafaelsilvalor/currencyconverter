package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.Favorite;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;

import java.util.List;
import java.util.Scanner;

public class FavoritesMenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final FavoritesManager favoritesManager;

    private static final int PAGE_SIZE = 5;
    private int currentPage = 0;

    public FavoritesMenuUI(CurrencyConverter converter, FavoritesManager favoritesManager) {
        this.converter = converter;
        this.favoritesManager = favoritesManager;
    }

    public void start() {
        while (true) {
            List<Favorite> allFavorites = favoritesManager.getFavorites();
            int totalPages = (int) Math.ceil((double) allFavorites.size() / PAGE_SIZE);
            List<Favorite> pageFavorites = getCurrentPageFavorites(allFavorites);

            System.out.println("\n==== Favorites ====");
            for (int i = 0; i < pageFavorites.size(); i++) {
                System.out.printf("%d) %s\n", i + 1, pageFavorites.get(i));
            }
            System.out.printf("Page %d/%d\n", currentPage + 1, Math.max(totalPages, 1));
            System.out.println("6) Previous page");
            System.out.println("7) Next page");
            System.out.println("8) Add new favorite");
            System.out.println("9) Remove a favorite");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1", "2", "3", "4", "5" -> handleFavoriteSelection(choice, pageFavorites);
                case "6" -> {
                    if (currentPage > 0) currentPage--;
                }
                case "7" -> {
                    if (currentPage < totalPages - 1) currentPage++;
                }
                case "8" -> addFavorite();
                case "9" -> removeFavorite(allFavorites);
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private List<Favorite> getCurrentPageFavorites(List<Favorite> allFavorites) {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allFavorites.size());
        return allFavorites.subList(start, end);
    }

    private void handleFavoriteSelection(String choice, List<Favorite> pageFavorites) {
        int index = Integer.parseInt(choice) - 1;
        if (index < pageFavorites.size()) {
            Favorite selected = pageFavorites.get(index);

            System.out.print("Enter amount to convert: ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount entered.");
                return;
            }

            double result = converter.convert(amount, selected.from(), selected.to());
            System.out.printf("== %.2f %s == %.2f %s%n", amount, selected.from(), result, selected.to());
        } else {
            System.out.println("Invalid favorite selection.");
        }
    }


    private void addFavorite() {
        System.out.print("From currency (e.g. USD): ");
        String from = scanner.nextLine().toUpperCase();
        System.out.print("To currency (e.g. BRL): ");
        String to = scanner.nextLine().toUpperCase();
        favoritesManager.addFavorite(new Favorite(from, to));
        System.out.println("Favorite added!");
    }

    private void removeFavorite(List<Favorite> allFavorites) {
        for (int i = 0; i < allFavorites.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, allFavorites.get(i));
        }
        System.out.print("Enter the number of the favorite to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < allFavorites.size()) {
                favoritesManager.removeFavorite(allFavorites.get(index));
                System.out.println("Favorite removed!");
            } else {
                System.out.println("Invalid index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
