package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for working with LW Lists.
 */
public class ListOperator extends AbstractComponentOperator {

	/**
	 * Initialize an ListOperator with the given list component.
	 */
	public ListOperator(Component list) {
		super(list);
	}

	/**
	 * Initialize a CheckboxOperator with a check box that has the specified
	 * name in the current context.
	 * 
	 * @param identifier
	 *            checkbox label or ToolTip text.
	 */
	public ListOperator(String identifier) {
		super(getComponentChooser(identifier));
	}

	private static ComponentChooser getComponentChooser(String identifier) {
		if (TextUtil.isNumeric(identifier)) {
			return new ByComponentTypeChooser(Integer.parseInt(identifier), ComponentType.LWList);
		} else {
			return new ByNameChooser(identifier, ComponentType.LWList);
		}
	}

	public void select(String identifier) {
		int index;
		if (TextUtil.isNumeric(identifier)) {
			index = Integer.parseInt(identifier);
		} else {
			String[] items = getItems();
			index = Arrays.asList(items).indexOf(identifier);
			if (index == -1) {
				throw new FormsLibraryException("Value '" + identifier + "' not fond in list: " + items);
			}
		}
		ObjectUtil.invokeMethodWithIntArg(getSource(), "select()", index);

		Method m;
		try {
			m = getSource().getClass().getSuperclass().getDeclaredMethod("_postItemEvent", String.class, int.class);
			m.setAccessible(true);
			m.invoke(getSource(), getItem(index), ItemEvent.SELECTED);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FormsLibraryException("Could not call method _selectItem");
		}

	}

	public String getSelected() {
		return (String) ObjectUtil.invokeMethod(getSource(), "getSelectedItem()");
	}

	public String getItem(int i) {
		return (String) ObjectUtil.invokeMethodWithIntArg(getSource(), "getItem()", i);
	}

	public String[] getItems() {
		return (String[]) ObjectUtil.invokeMethod(getSource(), "getItems()");
	}

}
