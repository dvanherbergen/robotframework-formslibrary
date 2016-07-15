package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.swing.context.Context;

public class ContextOperator {

    public Container getSource() {
        return (Container) Context.getContext().getSource();
    }

    public Component findComponent(String className) {
        return null;
    }

    public List<Component> findAllComponents(String... classNames) {
        return findChildComponentsByClass(getSource(), classNames);
    }

    /**
     * Find all childComponents of a given type
     */
    private List<Component> findChildComponentsByClass(Component component, String... classNames) {

        List<Component> result = new ArrayList<Component>();

        boolean isMatch = false;
        for (String className : classNames) {
            if (component.getClass().getName().equals(className)) {
                isMatch = true;
                break;
            }
        }
        if (classNames.length == 0) {
            isMatch = true;
        }

        if (isMatch) {
            result.add(component);
        } else if (component instanceof Container) {
            Component[] childComponents = ((Container) component).getComponents();
            for (Component child : childComponents) {
                result.addAll(findChildComponentsByClass(child, classNames));
            }
        }

        return result;
    }

    public void listComponents(String... componentTypes) {
        for (Component component : findAllComponents(componentTypes)) {
            String location = String.format("%1$-8s", component.getX() + "," + component.getY());
            System.out.println(location + " : " + ComponentUtil.getComponentName(component));
        }
    }

    public void listComponentHierarchy() {

        printHierarchyLevel(getSource(), 0);

    }

    private void printHierarchyLevel(Component component, int level) {

        String editable = "";
        if (ComponentUtil.isEditable(component)) {
            editable = " [editable] ";
        }
        String formattedName = String.format("%1$-" + (2 * (level + 1)) + "s", "L" + level) + component.getClass().getName() + "  -  "
                + ComponentUtil.getComponentName(component) + editable;
        System.out.println(formattedName);

        if (component instanceof Container) {
            Component[] childComponents = ((Container) component).getComponents();
            level++;
            for (Component child : childComponents) {
                printHierarchyLevel(child, level);
            }
            level--;
        }

    }
}
