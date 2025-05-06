package com.rafellor.currencyconverter.infrastructure.history;

import com.rafellor.currencyconverter.domain.ConversionRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConversionHistoryManager {
    private final Path historyFile;

    public ConversionHistoryManager(Path historyFile) {
        this.historyFile = historyFile;
    }

    public void save(ConversionRecord record) throws IOException {
        Files.write(
                historyFile,
                Collections.singletonList(record.serialize()),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public List<ConversionRecord> loadRecent(int maxEntries) throws IOException {
        if (!Files.exists(historyFile)) return Collections.emptyList();
        List<String> lines = Files.readAllLines(historyFile);
        int start = Math.max(0, lines.size() - maxEntries);
        return lines.subList(start, lines.size())
                .stream()
                .map(ConversionRecord::deserialize)
                .collect(Collectors.toList());
    }
}
