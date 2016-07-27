package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Chooser that selects the first component following a Label component with a
 * given text. Hidden components are ignored. This chooser can be used to find
 * text fields following a label field.
 */
public class ByPrecedingLabelChooser implements ComponentChooser {

    private String text;

    private boolean selectNextComponent = false;

    /**
     * Create new chooser for a component following a label.
     * 
     * @param text
     *            label value.
     */
    public ByPrecedingLabelChooser(String text) {
        this.text = text;
    }

    @Override
    public boolean checkComponent(Component component) {

        if (selectNextComponent) {
            Logger.info("Found component " + component.getName());
            selectNextComponent = false;
            return true;
        }

        if (ComponentType.LABEL.matches(component)) {
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
