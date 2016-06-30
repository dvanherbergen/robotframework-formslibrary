package org.robotframework.formslibrary.util;

import org.junit.Assert;
import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.swing.context.Context;

public class ContextUtil {

    public static void initRootContext() {

        Logger.info("No context selected. Defaulting to first window.");
        Context.setContext(FrameOperator.newOperatorFor(0));

    }

    public static void verifyContext() {

        try {
            Context.getContext();
        } catch (IllegalStateException e) {
            initRootContext();
        }

        String contextClass = Context.getContext().getSource().getClass().getName();
        if (!contextClass.equals("javax.swing.JFrame") && !contextClass.equals("oracle.forms.ui.ExtendedFrame")) {
            Assert.fail("Invalid context selected: " + contextClass);
        }

    }

}
