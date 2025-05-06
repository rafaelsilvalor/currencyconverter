package com.rafaellor.currencyconverter.cli;

import org.junit.jupiter.api.Test;

class MainTest {

    /**
     * A basic smoke test: invoking main with no arguments should not throw.
     */
    @Test
    void main_withNoArgs_doesNotThrow() {
        Main.main(new String[]{});
    }
}
