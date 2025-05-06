
package com.rafaellor.currencyconverter.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainIntegrationTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() throws IOException {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Ensure settings.properties exists and is set to English
        Files.writeString(Paths.get("settings/settings.properties"), """
language=en
country=US
""");
    }

//    @AfterEach
//    void restoreStreams() throws IOException {
//        System.setIn(originalIn);
//        System.setOut(originalOut);
//
//        // Clean up settings.properties after test
//        Files.deleteIfExists(Paths.get("settings/settings.properties"));
//    }

//    @Test
//    void testCliConversionFlow() throws Exception {
//        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.ENGLISH);
//
//        ProcessBuilder builder = new ProcessBuilder("java", "-jar", "target/currencyconverter.jar", "cc");
//        builder.redirectErrorStream(true);
//        Process process = builder.start();
//
//        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
//             BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//
//            writer.write("1000\n"); // Amount
//            writer.write("USD\n");  // From
//            writer.write("BRL\n");  // To
//            writer.flush();
//
//            String output = reader.lines().collect(Collectors.joining("\n"));
//            System.out.println("Captured CLI Output:\n" + output); // Debugging
//
//            assertTrue(output.contains(messages.getString("prompt.amount")));
//            assertTrue(output.contains(messages.getString("prompt.from")));
//            assertTrue(output.contains(messages.getString("prompt.to")));
//            assertTrue(output.contains("=="));
//        }
//
//        process.destroy();
//    }
}
