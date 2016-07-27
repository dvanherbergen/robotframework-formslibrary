package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with a single radio button option.
 */
public class RadioOperator extends AbstractComponentOperator {

    /**
     * Initialize a RadioOperator for the radio button option matching the given
     * name.
     */
    public RadioOperator(String identifier) {
        super(new ByNameChooser(identifier, ComponentType.EXTENDED_CHECKBOX));
    }

    /**
     * Select the radio option.
     */
    public void select() {
        ObjectUtil.invokeMethod(getSource(), "simulatePush()");
    }

    /**
     * Check if this radio option is selected.
     */
    public boolean isSelected() {
        return ObjectUtil.getBoolean(getSource(), "getState()");
    }
}
