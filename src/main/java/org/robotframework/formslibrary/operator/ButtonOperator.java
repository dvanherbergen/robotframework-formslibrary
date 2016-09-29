package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with (push)buttons.
 */
public class ButtonOperator extends AbstractComponentOperator {

	/**
	 * Initialize a ButtonOperator with the specified button component.
	 */
	public ButtonOperator(Component component) {
		super(component);
	}

	/**
	 * Initialize a ButtonOperator with a button with the specified name in the
	 * current context.
	 * 
	 * @param identifier
	 *            button label or ToolTip text.
	 */
	public ButtonOperator(String identifier) {
		super(new ByNameChooser(identifier, ComponentType.ALL_BUTTON_TYPES));
	}

	/**
	 * Simulate a push on the button.
	 */
	public void push() {
		if (!getSource().isEnabled()) {
			throw new FormsLibraryException("Button is not enabled.");
		}
		doPush();
		Logger.debug("Button was pushed.");
	}

	private void doPush() {
		ObjectUtil.invokeMethod(getSource(), "simulatePush()");

	}

	public void pushAsync() {
		if (!getSource().isEnabled()) {
			throw new FormsLibraryException("Button is not enabled.");
		}
		new Thread(() -> doPush()).start();
		Logger.debug("Button was pushed.");

	}

}
