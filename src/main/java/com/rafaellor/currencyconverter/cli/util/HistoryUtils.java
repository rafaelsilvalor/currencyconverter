package com.rafaellor.currencyconverter.cli.util;

import com.rafaellor.currencyconverter.domain.ConversionRecord;
import com.rafaellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryUtils {

    public static void recordHistory(ConversionHistoryManager manager,
                                     String from,
                                     String to,
                                     double amount,
                                     double result,
                                     ResourceBundle messages) {
        try {
            ConversionRecord record = new ConversionRecord(
                    LocalDateTime.now(),
                    from,
                    to,
                    BigDecimal.valueOf(amount),
                    BigDecimal.valueOf(result)
            );
            manager.save(record);
        } catch (IOException ioe) {

            System.out.println(
                    MessageFormat.format(messages.getString("error.history"), ioe.getMessage())
            );
        }
    }
    public static void showHistory(ConversionHistoryManager manager,
                                   ResourceBundle messages,
                                   int maxEntries) {
        try {
            List<ConversionRecord> recent = manager.loadRecent(maxEntries);
            if (recent.isEmpty()) {
                System.out.println(messages.getString("history.empty"));
            } else {
                recent.forEach(System.out::println);
            }
        } catch (IOException ioe) {
            System.out.println(
            MessageFormat.format(messages.getString("error.history"), ioe.getMessage())
            );
        }
    }
}
