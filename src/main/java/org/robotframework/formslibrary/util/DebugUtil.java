package org.robotframework.formslibrary.util;

import org.robotframework.formslibrary.FormsLibraryException;

/**
 * Utility class to keep track of the debug logging level and debug delays.
 */
public class DebugUtil {

    private static boolean debugEnabled;

    private static int keywordDelay = 0;

    /**
     * @return true if debug logging is enabled.
     */
    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     * Enable forms library debug logging.
     */
    public static void setDebugEnabled(boolean debugEnabled) {
        DebugUtil.debugEnabled = debugEnabled;
    }

    /**
     * Delay each operation creation with the given delay.
     * 
     * @param keywordDelay
     *            time in ms
     */
    public static void setKeywordDelay(int keywordDelay) {
        DebugUtil.keywordDelay = keywordDelay;
    }

    /**
     * To prevent screen actions from happening so fast that it is not possible
     * to view what is happening, we delay the constructing of operators with
     * the specified keyword delay.
     */
    public static void applyKeywordDelay() {
        if (keywordDelay > 0) {
            try {
                Thread.sleep(keywordDelay);
            } catch (InterruptedException e) {
                throw new FormsLibraryException(e);
            }
        }
    }
}
