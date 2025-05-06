package com.rafaellor.currencyconverter.cli.util;

import com.rafaellor.currencyconverter.domain.ConversionRecord;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HistoryUtilsTest {

    @Test
    void showHistory_emptyList_printsEmptyMessage() throws Exception {
        ConversionHistoryManager mgr = mock(ConversionHistoryManager.class);
        when(mgr.loadRecent(5)).thenReturn(Collections.emptyList());

        ResourceBundle msgs = mock(ResourceBundle.class);
        when(msgs.getString("history.empty")).thenReturn("No history available");

        String out = tapSystemOut(() ->
                HistoryUtils.showHistory(mgr, msgs, 5)
        );

        assertTrue(out.contains("No history available"),
                "Should print the empty-history message");
    }

    @Test
    void showHistory_nonEmpty_printsAllRecords() throws Exception {
        ConversionRecord a = new ConversionRecord(
                LocalDateTime.of(2025,1,1,10,0),
                "USD", "EUR",
                BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(0.9)
        );
        ConversionRecord b = new ConversionRecord(
                LocalDateTime.of(2025,1,2,11,0),
                "EUR", "JPY",
                BigDecimal.valueOf(2.0),
                BigDecimal.valueOf(250.0)
        );
        ConversionHistoryManager mgr = mock(ConversionHistoryManager.class);
        when(mgr.loadRecent(2)).thenReturn(Arrays.asList(a, b));

        ResourceBundle msgs = mock(ResourceBundle.class);

        String out = tapSystemOut(() ->
                HistoryUtils.showHistory(mgr, msgs, 2)
        );

        assertTrue(out.contains(a.toString()), "Should print first record");
        assertTrue(out.contains(b.toString()), "Should print second record");
    }

    @Test
    void showHistory_ioException_printsErrorMessage() throws Exception {
        ConversionHistoryManager mgr = mock(ConversionHistoryManager.class);
        when(mgr.loadRecent(3)).thenThrow(new IOException("fail"));

        ResourceBundle msgs = mock(ResourceBundle.class);
        when(msgs.getString("error.history")).thenReturn("History error: {0}");

        String out = tapSystemOut(() ->
                HistoryUtils.showHistory(mgr, msgs, 3)
        );

        assertTrue(out.contains("History error: fail"),
                "Should print formatted error message");
    }
}
