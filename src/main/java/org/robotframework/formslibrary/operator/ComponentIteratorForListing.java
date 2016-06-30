package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.robotframework.formslibrary.util.ComponentUtil;

public class ComponentIteratorForListing {

	private List<String> unformatted = new ArrayList<String>();
	private int level = 0;

	private ComponentIteratorForListing() {
	}

	public static List<String> getComponentList(Container container) {
		ComponentIteratorForListing cont = new ComponentIteratorForListing();
		cont.processComponent(container);
		return cont.unformatted;
	}

	private void processComponent(Component component) {
		operateOnComponent(component);
		level++;
		if (component instanceof Container) {
			Component[] subComponents = ((Container) component).getComponents();
			for (Component subComponent : subComponents) {
				processComponent(subComponent);
				level--;
			}
		}
	}

	private void operateOnComponent(Component component) {

		String componentType = component.getClass().getName();
		String componentName = ComponentUtil.getComponentName(component);
		String editable = "";
		if (ComponentUtil.isEditable(component)) {
			editable = " [editable] ";
		}
		String formattedName = spacesToFormatOutputAsTree(level) + "L" + level + "  -  " + componentType
				+ "  -  " + componentName + editable;

		unformatted.add(componentName);
		System.out.println(formattedName);
	}

	private String spacesToFormatOutputAsTree(int level) {
		String result = "";
		for (int i = 0; i < level; i++)
			result += "   ";
		return result;
	}
}
