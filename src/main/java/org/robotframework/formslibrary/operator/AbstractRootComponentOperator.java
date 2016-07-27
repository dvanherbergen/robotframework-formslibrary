package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.util.DebugUtil;

/**
 * Base class for all component operators which need to look for components to
 * operate on in the ROOT context.
 */
public abstract class AbstractRootComponentOperator extends ComponentOperator {

    /**
     * Create a new operator for an existing component.
     */
    public AbstractRootComponentOperator(Component component) {
        super(component);
    }

    /**
     * Create a new operator for a component. The component will be searched in
     * the ROOT context using the provided chooser.
     * 
     * @param chooser
     *            ComponentChooser to select component.
     */
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
