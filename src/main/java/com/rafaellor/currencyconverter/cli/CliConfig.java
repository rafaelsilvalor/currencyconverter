package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.infrastructure.config.ConfigLoader;


public final class CliConfig {
    private CliConfig() { }

    public static final String COMMAND =
            System.getProperty(
                    "cli.command",
                    ConfigLoader.get("cli.command", "cvc")
            );
}
