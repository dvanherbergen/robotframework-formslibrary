package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with checkboxes.
 */
public class CheckboxOperator extends AbstractComponentOperator {

    /**
     * Initialize an CheckboxOperator with the given check box component.
     */
    public CheckboxOperator(Component checkBox) {
        super(checkBox);
    }

    /**
     * Initialize a CheckboxOperator with a check box that has the specified
     * name in the current context.
     * 
     * @param identifier
     *            checkbox label or ToolTip text.
     */
    public CheckboxOperator(String identifier) {
        super(new ByNameChooser(identifier, ComponentType.CHECK_BOX));
    }

    public boolean isChecked() {
        return ObjectUtil.getBoolean(getSource(), "getState()");
    }

    public void verifyNotChecked() {
        if (isChecked()) {
            throw new FormsLibraryException("Checkbox is checked.");
        }
    }

    public void verifyChecked() {
        if (!isChecked()) {
            throw new FormsLibraryException("Checkbox is not checked.");
        }
    }

    public void check() {
        if (!isChecked()) {
            ObjectUtil.invokeMethod(getSource(), "simulatePush()");
        }
    }

    public void uncheck() {
        if (isChecked()) {
            ObjectUtil.invokeMethod(getSource(), "simulatePush()");
        }
    }
}
