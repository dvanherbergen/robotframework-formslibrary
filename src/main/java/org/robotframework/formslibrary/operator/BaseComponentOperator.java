package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.FormsLibraryException;
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
                throw new FormsLibraryException(e);
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
    
    /**
     * Simulate a key press/release event on the component.
     * @param key KeyEvent.VK_... 
     */
    protected void simulateKeyPressed(int key) {
    	getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_PRESSED, 0, 1, key, KeyEvent.CHAR_UNDEFINED));
        getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_RELEASED, 0, 2, key, KeyEvent.CHAR_UNDEFINED));
    }
    
    @Override
    public boolean isEnabled() {
        return getSource().isEnabled();
    }
}
