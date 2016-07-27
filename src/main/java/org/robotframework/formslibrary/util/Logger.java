package org.robotframework.formslibrary.util;

/**
 * Very, very simple logger implementation which allows debug logging to be
 * disabled.
 */
public class Logger {

    public static void debug(String message) {
        if (DebugUtil.isDebugEnabled()) {
            System.out.println("~ " + message);
        }
    }

    public static void info(String message) {
        System.out.println(message);
    }

    public static void error(Throwable t) {
        t.printStackTrace();
    }
}
