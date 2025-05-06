// src/test/java/com/rafellor/currencyconverter/cli/CommandLineDispatcherTest.java
//package com.rafaellor.currencyconverter.cli;
//
//import com.rafellor.currencyconverter.cli.handlers.CommandHandler;
//import com.rafellor.currencyconverter.cli.handlers.ListHandler;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CommandLineDispatcherTest {
//    private final ResourceBundle messages = ResourceBundle.getBundle("messages_en_US");
//
//    @Test
//    void dispatchesToFirstMatchingHandler() {
//        // fake handler that never matches
//        CommandHandler skip = (args) -> false;
//        // fake handler that handles "foo"
//        CommandHandler fooHandler = new CommandHandler() {
//            @Override public boolean matches(String[] args) { return args.length==1 && args[0].equals("foo"); }
//            @Override public void execute(String[] args) { System.out.print("HANDLED"); }
//        };
//
//        CommandLineDispatcher disp =
//                new CommandLineDispatcher(messages, List.of(skip, fooHandler));
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        disp.handle(new String[]{"foo"});
//        System.setOut(System.out);
//
//        assertEquals("HANDLED", out.toString());
//    }
//
//    @Test
//    void printsErrorWhenNoHandlerMatches() {
//        CommandLineDispatcher disp = new CommandLineDispatcher(messages, List.of());
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//
//        disp.handle(new String[]{"anything"});
//
//        System.setOut(System.out);
//        assertTrue(out.toString().contains(messages.getString("error.invalid.commandline")));
//    }
//}
