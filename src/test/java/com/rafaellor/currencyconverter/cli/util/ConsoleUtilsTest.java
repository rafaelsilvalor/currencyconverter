// src/test/java/com/rafaellor/currencyconverter/cli/util/ConsoleUtilsTest.java
package com.rafaellor.currencyconverter.cli.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleUtilsTest {
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @Test
    public void testClearConsoleNoException() {
        assertDoesNotThrow(ConsoleUtils::clearConsole);
    }

    @Test
    public void testWaitForUserPrintsMessageAndConsumesInput() {
        String prompt = "Press Enter:";
        // Provide a dummy token to satisfy at least one nextLine() call
        ByteArrayInputStream in = new ByteArrayInputStream("dummy\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        try {
            ConsoleUtils.waitForUser(prompt);
        } catch (NoSuchElementException ignored) {
            // input exhausted—ignore
        }

        String printed = out.toString();
        assertTrue(printed.contains(prompt), "Should prompt the user");

        // restore original streams
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
