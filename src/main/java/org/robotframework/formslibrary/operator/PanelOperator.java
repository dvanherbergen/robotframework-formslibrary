package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.chooser.ByComponentFreeTypeChooser;
import org.robotframework.swing.context.ContainerOperator;
import org.robotframework.swing.context.Context;

/**
 * Operator for working with Panels.
 */
public class PanelOperator extends ContainerOperator {

	/**
	 * Initialize a PanelOperator with the given type in the current context.
	 * 
	 * @param type
	 *            class type of the panel.
	 */
	public PanelOperator(String type) {
		super((org.netbeans.jemmy.operators.ContainerOperator) Context.getContext(), new ByComponentFreeTypeChooser(0, type));
	}

}
