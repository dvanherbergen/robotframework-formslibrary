package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.junit.Assert;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ObjectUtil;

public class CheckboxOperator extends BaseComponentOperator {

    public CheckboxOperator(Component checkBox) {
        super(checkBox);
    }

    public CheckboxOperator(String identifier) {
        super(new ByNameChooser(identifier, "oracle.ewt.lwAWT.LWCheckbox"));
    }

    public boolean isChecked() {
        return ObjectUtil.getBoolean(getSource(), "getState()");
    }

    public void verifyNotChecked() {
        if (isChecked()) {
            Assert.fail("Checkbox is checked.");
        }
    }

    public void verifyChecked() {
        if (!isChecked()) {
            Assert.fail("Checkbox is not checked.");
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
