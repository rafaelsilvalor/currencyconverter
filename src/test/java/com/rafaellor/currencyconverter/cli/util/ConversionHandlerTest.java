package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.application.CurrencyConverter;
import com.rafaellor.currencyconverter.cli.util.ConversionHandler;
import com.rafaellor.currencyconverter.domain.ConversionRecord;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversionHandlerTest {

    @Test
    void perform_shouldPrintConversionCurrencies_andSaveRecord() throws Exception {
        // Arrange
        double amount = 10.0;
        String from = "USD", to = "BRL";

        CurrencyConverter converter = mock(CurrencyConverter.class);
        when(converter.convert(amount, from, to)).thenReturn(50.0);

        ConversionHistoryManager history = mock(ConversionHistoryManager.class);
        ResourceBundle messages = mock(ResourceBundle.class);

        // Act
        String out = tapSystemOut(() ->
                ConversionHandler.perform(
                        amount, from, to, converter, history, messages
                )
        );

        // Assert it printed something like "== ... USD == ... BRL"
        assertTrue(out.trim().startsWith("=="), "Output should start with '=='");
        assertTrue(out.contains(" " + from), "Should include the from currency");
        assertTrue(out.contains(" " + to),   "Should include the to currency");

        // Verify record saved correctly
        ArgumentCaptor<ConversionRecord> captor = ArgumentCaptor.forClass(ConversionRecord.class);
        verify(history).save(captor.capture());
        String serialized = captor.getValue().serialize();
        String[] parts = serialized.split("\\|");
        assertEquals(from, parts[1], "serialize()[1] must be fromCurrency");
        assertEquals(to,   parts[2], "serialize()[2] must be toCurrency");
    }
}
