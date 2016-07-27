package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with Tabs.
 */
public class TabOperator extends AbstractComponentOperator {

    /**
     * Initialize a TabOperator with the first tabbar found in the current
     * context.
     */
    public TabOperator() {
        super(new ByComponentTypeChooser(0, ComponentType.TAB_BAR));
    }

    /**
     * Select the tab with the given name.
     */
    public void select(String name) {

        int tabCount = (Integer) ObjectUtil.invokeMethod(getSource(), "getItemCount");

        for (int i = 0; i < tabCount; i++) {
            Object tabItem = ObjectUtil.invokeMethodWithIntArg(getSource(), "getItem()", i);
            String label = ObjectUtil.getString(tabItem, "getLabel()");
            Logger.debug("Found tab " + label);
            if (name.equalsIgnoreCase(label)) {
                ObjectUtil.invokeMethodWithTypedArg(tabItem, "setSelected()", "boolean", Boolean.TRUE);
                return;
            }
        }

        throw new FormsLibraryException("Tab '" + name + "' not found.");
    }
}
