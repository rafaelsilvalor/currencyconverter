package com.rafellor.currencyconverter.cli;

import com.rafellor.currencyconverter.infrastructure.config.ConfigLoader;

public class CliConfig {
    private CliConfig(){}

    public static final String COMMAND =
            System.getProperty(
                    "cli.command",
                    ConfigLoader.get("cli.command", "cvc")
            );
}
