package com.rafellor.currencyconverter.cli;

public class Main {
    public static void main(String[] args) {
        CommandLineDispatcher dispatcher = new CommandLineDispatcher();
        dispatcher.handle(args);
    }
}
