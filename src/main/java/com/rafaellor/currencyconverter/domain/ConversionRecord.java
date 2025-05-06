package com.rafaellor.currencyconverter.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    // for file I/O: yyyy-MM-dd HH:mm:ss
    private static final DateTimeFormatter FILE_TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // for console display: yyyy-MM-dd HH:mm
    private static final DateTimeFormatter DISPLAY_TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final LocalDateTime timestamp;
    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal inputAmount;
    private final BigDecimal outputAmount;

    public ConversionRecord(LocalDateTime timestamp,
                            String fromCurrency,
                            String toCurrency,
                            BigDecimal inputAmount,
                            BigDecimal outputAmount) {
        this.timestamp     = timestamp;
        this.fromCurrency  = fromCurrency;
        this.toCurrency    = toCurrency;
        this.inputAmount   = inputAmount;
        this.outputAmount  = outputAmount;
    }

    /** Machine‑parseable, but with human‑friendly fields */
    public String serialize() {
        return String.join("|",
                timestamp.format(FILE_TS_FORMAT),
                fromCurrency,
                toCurrency,
                // force two decimals:
                inputAmount.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                outputAmount.setScale(2, RoundingMode.HALF_UP).toPlainString()
        );
    }

    public static ConversionRecord deserialize(String line) {
        String[] parts = line.split("\\|");
        LocalDateTime ts = LocalDateTime.parse(parts[0], FILE_TS_FORMAT);
        return new ConversionRecord(
                ts,
                parts[1],
                parts[2],
                // parsing back into BigDecimal
                new BigDecimal(parts[3]),
                new BigDecimal(parts[4])
        );
    }

    /** What you see when you print the record in the menu */
    @Override
    public String toString() {
        return String.format("[%s] %s %s → %s %s",
                timestamp.format(DISPLAY_TS_FORMAT),
                // two decimal places for amounts
                inputAmount.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                fromCurrency,
                outputAmount.setScale(2, RoundingMode.HALF_UP).toPlainString(),
                toCurrency
        );
    }
}
