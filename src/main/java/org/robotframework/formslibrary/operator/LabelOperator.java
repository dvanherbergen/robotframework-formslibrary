package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for working with labels.
 */
public class LabelOperator extends AbstractComponentOperator {

	/**
	 * Initialize a LabelOperator for the given label.
	 */
	public LabelOperator(Component component) {
		super(component);
	}

	/**
	 * Initialize a LabelOperator with a label with the specified name in the
	 * current context.
	 * 
	 * @param identifier
	 *            label.
	 */
	public LabelOperator(String identifier) {
		super(new ByNameChooser(identifier, ComponentType.LABEL, ComponentType.JRADIO_BUTTON, ComponentType.JLABEL));
	}

	/**
	 * @return field content.
	 */
	public String getValue() {
		return ObjectUtil.getString(getSource(), "getText()");
	}

	/**
	 * Verify that the field contains the given value.
	 */
	public void verifyValue(String value) {
		if (!TextUtil.matches(getValue(), value)) {
			throw new FormsLibraryException("Label value '" + getValue() + "' does not match " + value);
		}
		Logger.info("Value '" + getValue() + "' matches '" + value + "'.");
	}

}
