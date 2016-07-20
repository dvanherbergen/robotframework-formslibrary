package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.util.DebugUtil;

/**
 * Base class for all operators. Uses the ROOT context to resolve components.
 *
 */
public abstract class AbstractRootComponentOperator extends ComponentOperator {

    public AbstractRootComponentOperator(Component component) {
        super(component);
    }

    public AbstractRootComponentOperator(ComponentChooser chooser) {
        super(getContext(), chooser);
        DebugUtil.applyKeywordDelay();
    }

    /**
     * Get the ROOT context to use for locating the component.
     */
    private static ContainerOperator getContext() {
        return (ContainerOperator) FormsContext.getRootContext();
    }

}
