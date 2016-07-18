package org.robotframework.formslibrary.util;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.swing.context.Context;

public class ContextUtil {

    public static void initRootContext() {

        Logger.info("No context selected. Defaulting to first window.");
        Context.setContext(FrameOperator.newOperatorFor(0));

    }

    public static void verifyContext() {

    	// set root context if no context exists
        try {
            Context.getContext();
        } catch (IllegalStateException e) {
            initRootContext();
        }

        
        Component contextComponent = Context.getContext().getSource();
        String contextClass = contextComponent.getClass().getName();
        
        // verify that the current window context is still part of the desktop
        if (!FrameOperator.newOperatorFor(0).containsComponent(contextComponent)) {
        	Logger.info("Context '" + ComponentUtil.getComponentName(contextComponent) + "' no longer part of desktop.");
        	initRootContext();
        	Logger.info("Context changed to desktop.");
        }
        
        // verify the type of context
        if (!contextClass.equals(ComponentType.JFRAME) && !contextClass.equals(ComponentType.EXTENDED_FRAME)
                && !contextClass.equals(ComponentType.WINDOW)) {
            throw new FormsLibraryException("Invalid context selected: " + contextClass);
        }

        
    }

}
