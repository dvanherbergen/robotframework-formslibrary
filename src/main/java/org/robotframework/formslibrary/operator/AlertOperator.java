package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;

/**
 * Operator for working with error or warning dialog boxes.
 */
public class AlertOperator extends AbstractRootComponentOperator {

    /**
     * Initialize an AlertOperator with the first alert pane in the root
     * context.
     */
    public AlertOperator() {
        super(new ByComponentTypeChooser(0, ComponentType.ALERT_PANE));
    }

    /**
     * Initialize an AlertOperator with the given alert pane component.
     */
    public AlertOperator(Component component) {
        super(component);
    }

    /**
     * @return message displayed on alert pane.
     */
    public String getAlertMessage() {
        return ComponentUtil.getAccessibleText(getSource());
    }

}
