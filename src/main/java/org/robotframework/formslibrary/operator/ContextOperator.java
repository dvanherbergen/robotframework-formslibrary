package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.swing.context.Context;

public class ContextOperator {

    public Container getSource() {
        return (Container) Context.getContext().getSource();
    }

    public Component findComponent(ComponentChooser chooser) {
        List<Component> result = findChildComponentsByChooser(getSource(), chooser);
        if (result.isEmpty()) {
        	return null;
        } else {
        	return result.get(0);
        }
    }
    
    public List<Component> findAllComponents(ComponentChooser chooser) {
        return findChildComponentsByChooser(getSource(), chooser);
    }

    public List<Component> findAllComponents(String... classNames) {
        return findAllComponents(new ByClassChooser(0, classNames));
    }

    public Component findComponent(String... classNames) {
        return findComponent(new ByClassChooser(0, classNames));
    }
    
    /**
     * Find all childComponents that match a given chooser selection.
     */
    private List<Component> findChildComponentsByChooser(Component component, ComponentChooser chooser) {

        List<Component> result = new ArrayList<Component>();

        if (chooser.checkComponent(component)) {
            result.add(component);
        } else if (component instanceof Container) {
            Component[] childComponents = ((Container) component).getComponents();
            for (Component child : childComponents) {
                result.addAll(findChildComponentsByChooser(child, chooser));
            }
        }

        return result;
    }

    public void listComponents(String... componentTypes) {
        for (Component component : findAllComponents(new ByClassChooser(0, componentTypes))) {
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
