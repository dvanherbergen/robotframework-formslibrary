package org.robotframework.formslibrary.util;

import org.robotframework.formslibrary.FormsLibraryException;

public class DebugUtil {

    private static boolean debugEnabled;

    private static int keywordDelay = 0;

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void setDebugEnabled(boolean debugEnabled) {
        DebugUtil.debugEnabled = debugEnabled;
    }

    public static int getKeywordDelay() {
        return keywordDelay;
    }

    public static void setKeywordDelay(int keywordDelay) {
        DebugUtil.keywordDelay = keywordDelay;
    }

    /**
     * To prevent the screens from changing to fast, we delay the constructing
     * of operators with the specified keyword delay.
     */
    public static void applyKeywordDelay() {
        int delay = DebugUtil.getKeywordDelay();
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new FormsLibraryException(e);
            }
        }
    }
}
