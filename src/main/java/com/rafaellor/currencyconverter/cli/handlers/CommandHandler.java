package com.rafaellor.currencyconverter.cli.handlers;

/**
 * Defines CLI commands: matches args and executes action.
 */
public interface CommandHandler {
    boolean matches(String[] args);
    void execute(String[] args);
}
