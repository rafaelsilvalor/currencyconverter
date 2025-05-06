// src/test/java/com/rafaellor/currencyconverter/infrastructure/history/ConversionHistoryManagerTest.java
package com.rafaellor.currencyconverter.domain;

import com.rafaellor.currencyconverter.domain.ConversionRecord;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversionHistoryManagerTest {

    @TempDir Path tempDir;

    @Test
    void saveAndLoadRecent_shouldAppendAndReturnLastN() throws IOException {
        Path historyFile = tempDir.resolve("history.txt");
        ConversionHistoryManager mgr = new ConversionHistoryManager(historyFile);

        ConversionRecord first  = new ConversionRecord(
                LocalDateTime.of(2025,5,6,9,0), "USD","EUR",
                new BigDecimal("50"), new BigDecimal("44.20"));
        ConversionRecord second = new ConversionRecord(
                LocalDateTime.of(2025,5,6,10,0), "EUR","JPY",
                new BigDecimal("100"), new BigDecimal("13000"));

        mgr.save(first);
        mgr.save(second);

        // When maxEntries=1, only the second record should be returned
        List<ConversionRecord> lastOne = mgr.loadRecent(1);
        assertEquals(1, lastOne.size());
        assertEquals(second.toString(), lastOne.get(0).toString());

        // When maxEntries=2, both records in order
        List<ConversionRecord> lastTwo = mgr.loadRecent(2);
        assertEquals(2, lastTwo.size());
        assertEquals(first.toString(),  lastTwo.get(0).toString());
        assertEquals(second.toString(), lastTwo.get(1).toString());
    }

    @Test
    void loadRecent_onMissingFile_shouldReturnEmptyList() throws IOException {
        Path historyFile = tempDir.resolve("no-such-file.txt");
        ConversionHistoryManager mgr = new ConversionHistoryManager(historyFile);
        assertTrue(mgr.loadRecent(5).isEmpty());
    }
}
