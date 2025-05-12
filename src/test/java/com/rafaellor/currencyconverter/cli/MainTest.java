package com.rafaellor.currencyconverter.cli;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    @Test
    void main_withNoArgs_doesNotThrow_and_exitsMenu() throws Exception {
        // Provide '0' to select exit in the interactive menu
        SystemLambda.withTextFromSystemIn("0\n")
                .execute(() -> assertDoesNotThrow(() -> Main.main(new String[0])));
    }
}
