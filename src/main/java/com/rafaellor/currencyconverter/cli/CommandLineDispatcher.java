package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.cli.handlers.CommandHandler;
import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class CommandLineDispatcher {
    private final List<CommandHandler> handlers = new ArrayList<>();
    private final ResourceBundle messages;

    public CommandLineDispatcher(ResourceBundle messages) {
        this.messages = messages;
        Reflections reflections = new Reflections(
                "com.rafaellor.currencyconverter.cli.handlers"
        );
        Set<Class<? extends CommandHandler>> classes = reflections.getSubTypesOf(CommandHandler.class);
        for (Class<? extends CommandHandler> cls : classes) {
            try {
                Constructor<? extends CommandHandler> ctor = cls.getDeclaredConstructor(ResourceBundle.class);
                handlers.add(ctor.newInstance(messages));
            } catch (Exception ex) {
                throw new RuntimeException("Failed to load handler class " + cls.getName(), ex);
            }
        }
    }

    public void handle(String[] args) {
        for (CommandHandler h : handlers) {
            if (h.matches(args)) {
                h.execute(args);
                return;
            }
        }
        System.out.println(messages.getString("error.invalid.commandline"));
    }
}
