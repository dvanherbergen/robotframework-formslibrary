package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

public class TabOperator extends BaseComponentOperator {

    public TabOperator() {
        super(new ByClassChooser(0, ComponentType.TAB_BAR));
    }

    public void select(String name) {

        int tabCount = (Integer) ObjectUtil.invoke(getSource(), "getItemCount");

        for (int i = 0; i < tabCount; i++) {
            Object tabItem = ObjectUtil.invokeWithIntArg(getSource(), "getItem()", i);
            String label = ObjectUtil.getString(tabItem, "getLabel()");
            Logger.debug("Found tab " + label);
            if (name.equalsIgnoreCase(label)) {
                ObjectUtil.invokeMethodWithTypedArg(tabItem, "setSelected()", "boolean", Boolean.TRUE);
                return;
            }
        }

        throw new FormsLibraryException("Tab " + name + " not found.");
    }
}
