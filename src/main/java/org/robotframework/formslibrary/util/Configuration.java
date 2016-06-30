package org.robotframework.formslibrary.util;

public class Configuration {

    private static boolean debugEnabled;

    private static int keywordDelay = 0;

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void setDebugEnabled(boolean debugEnabled) {
        Configuration.debugEnabled = debugEnabled;
    }

    public static int getKeywordDelay() {
        return keywordDelay;
    }

    public static void setKeywordDelay(int keywordDelay) {
        Configuration.keywordDelay = keywordDelay;
    }

}
