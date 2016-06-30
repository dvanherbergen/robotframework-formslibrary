package org.robotframework.formslibrary.operator;

import org.junit.Assert;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.swing.button.AbstractButtonOperator;

public class ButtonOperator extends BaseComponentOperator implements AbstractButtonOperator {

    public ButtonOperator(String identifier) {
        super(new ByNameChooser(identifier, "oracle.ewt.button.PushButton", "oracle.forms.ui.VButton"));
    }

    @Override
    public boolean isEnabled() {
        return getSource().isEnabled();
    }

    @Override
    public boolean isSelected() {
        return ObjectUtil.getBoolean(getSource(), "isActive()");
    }

    @Override
    public void push() {
        if (!getSource().isEnabled()) {
            Assert.fail("Button is not enabled.");
        }
        ObjectUtil.invoke(getSource(), "simulatePush()");
        System.out.println("Button was pushed.");
    }

}
