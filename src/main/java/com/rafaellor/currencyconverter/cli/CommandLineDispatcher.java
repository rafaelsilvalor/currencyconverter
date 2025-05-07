package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.cli.handlers.CommandHandler;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Discovers and dispatches CLI command handlers.
 */
public class CommandLineDispatcher {
    private final List<CommandHandler> handlers = new ArrayList<>();
    private final ResourceBundle messages;

    public CommandLineDispatcher(ResourceBundle messages) {
        this.messages = messages;

        // Scan for all CommandHandler implementations
        Reflections reflections = new Reflections("com.rafaellor.currencyconverter.cli.handlers");
        for (Class<? extends CommandHandler> handlerClass
                : reflections.getSubTypesOf(CommandHandler.class)) {

            CommandHandler handlerInstance;
            // 1) Try constructor(ResourceBundle)
            try {
                Constructor<? extends CommandHandler> rbCtor =
                        handlerClass.getConstructor(ResourceBundle.class);
                handlerInstance = rbCtor.newInstance(messages);

            } catch (NoSuchMethodException e1) {
                // 2) Fallback to no-arg constructor
                try {
                    Constructor<? extends CommandHandler> noArgCtor =
                            handlerClass.getConstructor();
                    handlerInstance = noArgCtor.newInstance();

                } catch (NoSuchMethodException
                         | InstantiationException
                         | IllegalAccessException
                         | InvocationTargetException e2) {
                    throw new RuntimeException(
                            "Cannot instantiate handler " + handlerClass.getName(), e2);
                }

            } catch (InstantiationException
                     | IllegalAccessException
                     | InvocationTargetException e) {
                throw new RuntimeException(
                        "Failed to construct handler " + handlerClass.getName(), e);
            }

            handlers.add(handlerInstance);
        }
    }

    /**
     * Finds the first handler whose matches(...) returns true, and invokes execute(...).
     * If none match, prints an error message.
     */
    public void handle(String[] args) {
        for (CommandHandler handler : handlers) {
            if (handler.matches(args)) {
                handler.execute(args);
                return;
            }
        }
        System.out.println(messages.getString("error.invalid.commandline"));
    }
}
