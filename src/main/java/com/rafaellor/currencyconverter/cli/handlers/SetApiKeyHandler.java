package com.rafaellor.currencyconverter.cli.handlers;

import com.rafaellor.currencyconverter.infrastructure.config.FolderInitializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Handler for setting the API key via command-line:
 *   --set-apikey <apikey>
 * Persists the provided API key into settings/config.properties under 'api.key'.
 */
public class SetApiKeyHandler implements CommandHandler {
    private final ResourceBundle messages;
    private static final String SETTINGS_DIR     = "settings";
    private static final String CONFIG_FILE      = "config.properties";
    private static final String API_KEY_PROPERTY = "API_KEY";

    public SetApiKeyHandler(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public boolean matches(String[] args) {
        return args.length == 2 && "--set-apikey".equals(args[0]);
    }

    @Override
    public void execute(String[] args) {
        // Strip any "API_KEY=" prefix if the user provided it
        String raw = args[1];
        String apiKey = raw.contains("=") ? raw.substring(raw.indexOf('=') + 1) : raw;

        try {
            // Ensure folders exist
            FolderInitializer.initialize();
            Path configPath = Path.of(SETTINGS_DIR).resolve(CONFIG_FILE);

            // Load existing properties if present
            Properties props = new Properties();
            if (Files.exists(configPath)) {
                try (InputStream in = Files.newInputStream(configPath)) {
                    props.load(in);
                }
            }

            // Set the API key and save
            props.setProperty(API_KEY_PROPERTY, apiKey);
            try (OutputStream out = Files.newOutputStream(
                    configPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                props.store(out, "Updated API key via --set-apikey");
            }

            System.out.println("✅ API key set successfully and saved to " + configPath);
        } catch (IOException e) {
            System.err.println("❌ Failed to set API key: " + e.getMessage());
        }
    }
}
