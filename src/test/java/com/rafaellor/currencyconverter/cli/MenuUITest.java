package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuUITest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private ResourceBundle bundle;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(output));
        bundle = ResourceBundle.getBundle("messages");
    }

//    @Test
//    void shouldShowSupportedCurrencies() {
//        // Simulate input: 3 to list, then press enter, then 0 to exit
//        String input = "3\n\n0\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        ExchangeRateService mockService = new ExchangeRateService() {
//            @Override
//            public double getExchangeRate(String from, String to) {
//                return 1.0;
//            }
//
//            @Override
//            public List<String> getSupportedCodes() {
//                return List.of("USD", "BRL", "EUR");
//            }
//        };
//
//        CurrencyConverter converter = new CurrencyConverter(mockService);
//        MenuUI ui = new MenuUI(converter, mockService, bundle);
//        ui.start();
//
//        String out = output.toString();
//        assertTrue(out.contains("USD"));
//        assertTrue(out.contains("BRL"));
//        assertTrue(out.contains("EUR"));
//    }
}
