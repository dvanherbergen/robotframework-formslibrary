package org.robotframework.formslibrary.context;

import java.util.List;

import org.robotframework.formslibrary.operator.WindowOperator;
import org.robotframework.formslibrary.util.Logger;

/**
 * Monitor the open windows before and after an action. When a new window is
 * detected or the current window was closed, the current context will be
 * changed accordingly.
 */
public class ContextChangeMonitor {

    private List<String> initialOpenWindows;

    /**
     * Start monitoring for window changes.
     */
    public void start() {
        initialOpenWindows = new WindowOperator().getWindowTitles();
    }

    /**
     * Stop monitoring for window changes and change the context if needed. When
     * a new window is detected, it will be automatically set as the new
     * context. When the current context window was closed, the context will be
     * reset to the root context.
     */
    public void stop() {
        List<String> newOpenWindows = new WindowOperator().getWindowTitles();

        // check if a window was closed
        if (newOpenWindows.size() < initialOpenWindows.size()) {
            FormsContext.resetContext();
            return;
        }

        // check for new windows
        for (String name : newOpenWindows) {
            if (!initialOpenWindows.contains(name)) {
                // there is a new window open
                Logger.info("Found new window '" + name + "', autosetting context to new window.");
                new WindowOperator().setWindowAsContext(name);
                return;
            }
        }

    }
}
