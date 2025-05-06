package com.rafaellor.currencyconverter.cli.util;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }

    public static void waitForUser(String message) {
        System.out.print(message);
        scanner.nextLine();
    }
}
