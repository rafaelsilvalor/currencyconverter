package com.rafellor.currencyconverter.cli.util;

import com.rafellor.currencyconverter.application.CurrencyConverter;
import com.rafellor.currencyconverter.domain.ConversionRecord;
import com.rafellor.currencyconverter.infrastructure.history.ConversionHistoryManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ConversionHandler {
    public static void perform(double amount,
                               String from,
                               String to,
                               CurrencyConverter converter,
                               ConversionHistoryManager historyManager,
                               ResourceBundle messages) {
        double result = converter.convert(amount, from, to);
        System.out.printf("== %.2f %s == %.2f %s%n", amount, from, result, to);

        try {
            ConversionRecord record = new ConversionRecord(
                    LocalDateTime.now(),
                    from,
                    to,
                    BigDecimal.valueOf(amount),
                    BigDecimal.valueOf(result)
            );
            historyManager.save(record);
        } catch (IOException ioe) {
            System.out.println(MessageFormat.format(messages.getString("error.history"), ioe.getMessage())
            );
        }
    }
}
