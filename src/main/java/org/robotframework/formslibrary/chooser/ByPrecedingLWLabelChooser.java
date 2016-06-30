package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Select the first component following an LWLabel with the expected text.
 *
 */
public class ByPrecedingLWLabelChooser implements ComponentChooser {

    private String text;

    private boolean selectNextComponent = false;

    public ByPrecedingLWLabelChooser(String text) {
        this.text = text;
    }

    @Override
    public boolean checkComponent(Component component) {

        if (selectNextComponent) {
            Logger.info("Found component " + component.getName());
            selectNextComponent = false;
            return true;
        }

        if (component.getClass().getName().equals("oracle.ewt.lwAWT.LWLabel")) {
            String label = ObjectUtil.getString(component, "getText()");
            if (label != null) {
                label = label.replaceAll(":", "").trim();
            }
            if (text.equalsIgnoreCase(label)) {
                Logger.debug("Found matching label " + label);
                selectNextComponent = true;
                return false;
            } else {
                Logger.debug("Found label " + label);
            }
        }
        selectNextComponent = false;
        return false;
    }

    @Override
    public String getDescription() {

        return text;
    }

}
