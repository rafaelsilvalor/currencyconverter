package com.rafaellor.currencyconverter.cli;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void main_withNoArgs_doesNotThrow() {
        Main.main(new String[]{});
    }
}
