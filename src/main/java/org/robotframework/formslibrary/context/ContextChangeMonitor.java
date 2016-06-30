package org.robotframework.formslibrary.context;

import java.util.List;

import org.robotframework.formslibrary.operator.WindowOperator;
import org.robotframework.formslibrary.util.Logger;

/**
 * Monitor the open windows before and after an action. When a new window is
 * detected, it will be automatically set as the new context.
 */
public class ContextChangeMonitor {

    private List<String> initialOpenWindows;

    public void start() {
        initialOpenWindows = new WindowOperator().getWindowTitles();
    }

    public void stop() {
        List<String> newOpenWindows = new WindowOperator().getWindowTitles();
        for (String name : newOpenWindows) {
            if (!initialOpenWindows.contains(name)) {
                // there is a new window open
                Logger.info("Found new window '" + name + "', autosetting context to new window.");
                new WindowOperator().setWindowAsContext(name);
                break;
            }
        }
    }
}
