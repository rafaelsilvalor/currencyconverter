package com.rafaellor.currencyconverter.cli;

import com.rafaellor.currencyconverter.cli.handlers.CommandHandler;
import com.rafaellor.currencyconverter.cli.handlers.ListHandler;
import com.rafaellor.currencyconverter.cli.handlers.MenuHandler;
import com.rafaellor.currencyconverter.cli.handlers.OneLineConversion;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Dispatches CLI commands based on first argument, supporting both legacy and new flags.
 */
public class CommandLineDispatcher {
    private final ResourceBundle messages;
    private final List<CommandHandler> handlers = new ArrayList<>();

    public CommandLineDispatcher(ResourceBundle messages) {
        this.messages = messages;
        // Auto-discover handlers via reflections
        Reflections refs = new Reflections("com.rafaellor.currencyconverter.cli.handlers");
        for (Class<? extends CommandHandler> cls : refs.getSubTypesOf(CommandHandler.class)) {
            try {
                handlers.add(cls.getDeclaredConstructor(ResourceBundle.class).newInstance(messages));
            } catch (Exception e) {
                throw new RuntimeException("Failed to construct handler " + cls.getName(), e);
            }
        }
    }

    public void handle(String[] args) {
//        // Guard clause: no args
//        if (args.length == 0) {
//            System.err.println("!! CLI Error: No command provided");
//            return;
//        }
            if (args.length == 0) {
                    // No flags → launch interactive menu
                           new MenuHandler(messages).execute(new String[0]);
                    return;
                }

        // Normalize legacy oneline flag
//        if ("--oneline".equals(args[0])) {
//            args[0] = "--convert";
//        }

        // Attempt to match and execute a handler
        for (CommandHandler handler : handlers) {
            if (handler.matches(args)) {
                handler.execute(args);
                return;
            }
        }

        // No handler matched → error message
        System.out.println(messages.getString("error.invalid.commandline"));

    }
}
