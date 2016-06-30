package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.junit.Assert;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.util.Configuration;
import org.robotframework.formslibrary.util.ContextUtil;
import org.robotframework.swing.context.Context;

public abstract class BaseComponentOperator extends ComponentOperator {

    public BaseComponentOperator(Component component) {
        super(component);
    }

    public BaseComponentOperator(ComponentChooser chooser) {
        super(getContext(), chooser);

        // if a delay is set, we slow everything down
        // to make it easier to follow the actions on screen
        int delay = Configuration.getKeywordDelay();
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }
        }

    }

    /**
     * Get current context. If no context is set, it will be defaulted to the
     * first window.
     */
    private static ContainerOperator getContext() {

        ContextUtil.verifyContext();
        return (ContainerOperator) Context.getContext();

    }
}
