package com.rafaellor.currencyconverter.infrastructure.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {

    @Test
    void shouldLoadApiFromPropertiesFile() {
        ConfigLoader configLoader = new ConfigLoader();
        String apiKey = configLoader.get("API_KEY");

        assertNotNull(apiKey, "API_KEY should not be null");
        assertFalse(apiKey.isBlank(), "API_KEY should not be blank");
    }
}
