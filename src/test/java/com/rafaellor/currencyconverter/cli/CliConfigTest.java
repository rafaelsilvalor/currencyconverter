//package com.rafaellor.currencyconverter.cli;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CliConfigTest {
//
//    private static final String ENV_PROP = "cli.command";
//
//    @AfterEach
//    void tearDown() {
//        System.clearProperty(ENV_PROP);
//    }
//
//    /**
//     * By default (no system property set), COMMAND should fall back to "cvc".
//     */
//    @Test
//    void defaultCommand_isCvc() {
//        // Ensure the system property is not set
//        System.clearProperty(ENV_PROP);
//
//        // The COMMAND constant is read at class load time, so we expect the default
//        assertEquals("cvc", CliConfig.COMMAND);
//    }
//
//    /**
//     * If the user sets -Dcli.command=foo, System.getProperty should reflect that.
//     * Note: because COMMAND is a public static final field, it is inlined at compile time
//     * and cannot be changed at runtime. You may need to expose a non-final accessor
//     * to test this behavior directly.
//     */
//    @Test
//    void systemProperty_overrideReflectsInSystem() {
//        System.setProperty(ENV_PROP, "foo");
//        assertEquals("foo", System.getProperty(ENV_PROP));
//    }
//}
