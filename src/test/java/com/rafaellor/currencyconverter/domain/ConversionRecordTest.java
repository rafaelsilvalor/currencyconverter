package com.rafaellor.currencyconverter.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ConversionRecordTest {

    @Test
    void serializeDeserialize_roundTripsViaSerialize() {
        LocalDateTime ts = LocalDateTime.of(2025, 5, 6, 12, 0, 0);
        ConversionRecord original =
                new ConversionRecord(ts, "USD", "BRL",
                        new BigDecimal("100"), new BigDecimal("565.59"));

        // round‑trip through serialize()/deserialize()
        String serialized = original.serialize();
        ConversionRecord parsed = ConversionRecord.deserialize(serialized);

        // parsed.serialize() must match the original
        assertEquals(serialized, parsed.serialize());
    }

    @Test
    void toString_matchesConsoleFormat() {
        LocalDateTime ts = LocalDateTime.of(2025, 5, 6, 14, 30, 0);
        ConversionRecord rec = new ConversionRecord(
                ts, "EUR", "JPY", new BigDecimal("2500"), new BigDecimal("3000"));

        String fmt = ts.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        // NOTE: toString uses two‑decimal formatting on the amounts
        String expected = String.format("[%s] 2500.00 EUR → 3000.00 JPY", fmt);

        assertEquals(expected, rec.toString());
    }
}
