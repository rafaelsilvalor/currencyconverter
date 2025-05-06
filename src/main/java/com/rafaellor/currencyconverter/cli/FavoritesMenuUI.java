package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.cli.util.ConversionHandler;
import com.rafellor.currencyconverter.domain.Favorite;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import com.rafellor.currencyconverter.infrastructure.favorites.FavoritesManager;
import com.rafellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.rafellor.currencyconverter.cli.util.ConsoleUtils.clearConsole;
import static com.rafellor.currencyconverter.cli.util.ConsoleUtils.waitForUser;

public class FavoritesMenuUI {
    private final Scanner scanner = new Scanner(System.in);
    private final CurrencyConverter converter;
    private final ExchangeRateService client;
    private final FavoritesManager favoritesManager;
    private final ConversionHistoryManager historyManager;
    private final ResourceBundle messages;

    private static final int PAGE_SIZE = 5;
    private int currentPage = 0;

    public FavoritesMenuUI(CurrencyConverter converter,
                           ExchangeRateService client,
                           FavoritesManager favoritesManager,
                           ConversionHistoryManager historyManager,
                           ResourceBundle messages) {
        this.converter        = converter;
        this.client           = client;
        this.favoritesManager = favoritesManager;
        this.historyManager   = historyManager;
        this.messages         = messages;
    }

    public void start() {
        while (true) {
            clearConsole();
            List<Favorite> allFavorites = favoritesManager.getFavorites();
            int totalPages = (int) Math.ceil((double) allFavorites.size() / PAGE_SIZE);
            List<Favorite> pageFavorites = getCurrentPageFavorites(allFavorites);

            System.out.println("\n==== " + messages.getString("favorites.title") + " ====\n");
            for (int i = 0; i < pageFavorites.size(); i++) {
                System.out.printf("%d) %s\n", i + 1, pageFavorites.get(i));
            }
            System.out.println();
            String pageInfo = MessageFormat.format(messages.getString("favorites.page"),
                    currentPage + 1, Math.max(totalPages, 1));
            System.out.println(pageInfo);

            System.out.println("\n6) " + messages.getString("favorites.option.previous"));
            System.out.println("7) " + messages.getString("favorites.option.next") + "\n");
            System.out.println("8) " + messages.getString("favorites.option.add"));
            System.out.println("9) " + messages.getString("favorites.option.remove"));
            System.out.println("0) " + messages.getString("favorites.option.back") + "\n");
            System.out.print(messages.getString("menu.prompt") + " ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1", "2", "3", "4", "5" -> handleFavoriteSelection(choice, pageFavorites);
                case "6" -> { if (currentPage > 0) currentPage--; }
                case "7" -> { if (currentPage < totalPages - 1) currentPage++; }
                case "8" -> addFavorite();
                case "9" -> removeFavorite(allFavorites);
                case "0" -> { return; }
                default  -> System.out.println(messages.getString("error.invalid.option"));
            }
        }
    }

    private List<Favorite> getCurrentPageFavorites(List<Favorite> allFavorites) {
        int start = currentPage * PAGE_SIZE;
        int end   = Math.min(start + PAGE_SIZE, allFavorites.size());
        return allFavorites.subList(start, end);
    }

    private void handleFavoriteSelection(String choice, List<Favorite> pageFavorites) {
        int index = Integer.parseInt(choice) - 1;
        if (index < pageFavorites.size()) {
            Favorite selected = pageFavorites.get(index);
            try {


                System.out.print(messages.getString("prompt.amount") + " ");
                double amount = Double.parseDouble(scanner.nextLine());

                ConversionHandler.perform(
                        amount,
                        selected.from(),
                        selected.to(),
                        converter,
                        historyManager,
                        messages
                );

            } catch (NumberFormatException e) {
                System.out.println(messages.getString("favorites.invalid.amount"));
            }

        } else {
            System.out.println(messages.getString("favorites.invalid.selection"));
        }
        waitForUser(messages.getString("prompt.continue"));
    }

    private void addFavorite() {
        clearConsole();
        System.out.print(messages.getString("favorites.prompt.from") + " ");
        String from = scanner.nextLine().toUpperCase();
        System.out.print(messages.getString("favorites.prompt.to") + " ");
        String to = scanner.nextLine().toUpperCase();

        List<String> validCodes = client.getSupportedCodes().stream()
                .map(line -> line.substring(0, 3))
                .toList();

        if (!validCodes.contains(from) || !validCodes.contains(to)) {
            System.out.println(messages.getString("favorites.unsupported.code"));
            return;
        }

        favoritesManager.addFavorite(new Favorite(from, to));
        System.out.println(messages.getString("favorites.added"));
        waitForUser(messages.getString("prompt.continue"));
    }

    private void removeFavorite(List<Favorite> allFavorites) {
        boolean done = false;
        while (!done) {
            clearConsole();
            System.out.println("\n==== " + messages.getString("favorites.option.remove") + " ====\n");
            System.out.println("0) " + messages.getString("favorites.option.back") + "\n");
            for (int i = 0; i < allFavorites.size(); i++) {
                System.out.printf("%d) %s%n", i + 1, allFavorites.get(i));
            }
            System.out.print("\n" + messages.getString("favorites.prompt.select") + " ");
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("favorites.invalid.input"));
                waitForUser(messages.getString("prompt.continue"));
                continue;
            }
            if (choice == 0) {
                done = true;
            } else if (choice >= 1 && choice <= allFavorites.size()) {
                favoritesManager.removeFavorite(allFavorites.get(choice - 1));
                System.out.println(messages.getString("favorites.removed"));
                done = true;
            } else {
                System.out.println(messages.getString("favorites.invalid.index"));
                waitForUser(messages.getString("prompt.continue"));
            }
        }
    }
}