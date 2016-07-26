package org.robotframework.formslibrary.context;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

public class FormsContext {

    private static ComponentWrapper context;

    public static void setContext(ComponentWrapper operator) {
        context = operator;
        Context.setContext(context);
    }

    /**
     * Get current context. If no context is set, it will be defaulted to the
     * first window.
     */
    public static ComponentWrapper getContext() {

        // set root context if no context exists
        if (context == null) {
            Logger.debug("No context found.");
            resetContext();
            return context;
        }

        Component contextComponent = Context.getContext().getSource();
        String contextClass = contextComponent.getClass().getName();

        // verify that the current window context is still part of the desktop
        if (!new FrameOperator().containsComponent(contextComponent)) {
            Logger.info("Context " + ComponentUtil.getFormattedComponentNames(contextComponent) + " is no longer part of desktop.");
            resetContext();
            return context;
        }

        // verify the type of context
        if (!contextClass.equals(ComponentType.JFRAME) && !contextClass.equals(ComponentType.EXTENDED_FRAME)
                && !contextClass.equals(ComponentType.WINDOW)) {
            throw new FormsLibraryException("Invalid context selected: " + contextClass);
        }

        return context;
    }

    /**
     * @return root context (the forms desktop window)
     */
    public static ComponentWrapper getRootContext() {
        return new FrameOperator();
    }

    /**
     * Change the context to the root context.
     */
    public static void resetContext() {
        Logger.info("Changing context to main desktop window.");
        context = getRootContext();
        Context.setContext(context);
    }
}
