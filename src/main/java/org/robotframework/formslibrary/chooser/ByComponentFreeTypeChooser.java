package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

/**
 * Chooser to select Oracle Forms components based on their type (class) and
 * index (occurrence). Hidden components are ignored.
 */
public class ByComponentFreeTypeChooser implements ComponentChooser {

	private String type;
	private int index;

	/**
	 * @param index
	 *            Specifies which occurrence of the component in the context to
	 *            select. Use -1 to select all occurrences.
	 * @param type
	 *            Specifies which component types to include.
	 */
	public ByComponentFreeTypeChooser(int index, String type) {
		this.index = index;
		this.type = type;
	}

	@Override
	public boolean checkComponent(Component component) {

		if (type.matches(component.getClass().getName()) && component.isShowing()) {
			if (index <= 0) {
				return true;
			} else {
				index--;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return type;
	}

}
