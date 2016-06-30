package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ObjectUtil;

public class RadioOperator extends BaseComponentOperator {

    public RadioOperator(String identifier) {
        super(new ByNameChooser(identifier, "oracle.forms.ui.ExtendedCheckbox"));
    }

    public void select() {
        ObjectUtil.invoke(getSource(), "simulatePush()");
    }
}
