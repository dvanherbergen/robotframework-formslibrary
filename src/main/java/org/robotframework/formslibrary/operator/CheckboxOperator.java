package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

public class CheckboxOperator extends AbstractComponentOperator {

    public CheckboxOperator(Component checkBox) {
        super(checkBox);
    }

    public CheckboxOperator(String identifier) {
        super(new ByNameChooser(identifier, ComponentType.LW_CHECK_BOX));
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
            ObjectUtil.invoke(getSource(), "simulatePush()");
        }
    }

    public void uncheck() {
        if (isChecked()) {
            ObjectUtil.invoke(getSource(), "simulatePush()");
        }
    }
}
