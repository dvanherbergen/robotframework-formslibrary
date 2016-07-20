package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.swing.button.AbstractButtonOperator;

public class ButtonOperator extends AbstractComponentOperator implements AbstractButtonOperator {

    public ButtonOperator(String identifier) {
        super(new ByNameChooser(identifier, ComponentType.ALL_BUTTON_TYPES));
    }

    @Override
    public boolean isSelected() {
        return ObjectUtil.getBoolean(getSource(), "isActive()");
    }

    @Override
    public void push() {
        if (!getSource().isEnabled()) {
            throw new FormsLibraryException("Button is not enabled.");
        }
        ObjectUtil.invoke(getSource(), "simulatePush()");
        System.out.println("Button was pushed.");
    }

}
