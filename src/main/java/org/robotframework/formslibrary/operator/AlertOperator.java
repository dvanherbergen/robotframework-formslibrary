package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;

public class AlertOperator extends AbstractRootComponentOperator {

    public AlertOperator(Component component) {
        super(component);
    }

    public AlertOperator() {
        super(new ByClassChooser(0, ComponentType.ALERT_PANE));
    }

    public String getAlertMessage() {
        return ComponentUtil.getAccessibleText(getSource());
    }

}
