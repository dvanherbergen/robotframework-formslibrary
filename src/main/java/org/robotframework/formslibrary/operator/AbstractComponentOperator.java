package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.util.DebugUtil;

/**
 * Base class for all operators. Uses the current context to resolve components.
 *
 */
public abstract class AbstractComponentOperator extends ComponentOperator {

    public AbstractComponentOperator(Component component) {
        super(component);
    }

    public AbstractComponentOperator(ComponentChooser chooser) {
        super(getContext(), chooser);
        DebugUtil.applyKeywordDelay();
    }

    /**
     * Get the CURRENT context to use for locating the component.
     */
    private static ContainerOperator getContext() {
        return (ContainerOperator) FormsContext.getContext();
    }

}
