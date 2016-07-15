package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.robotframework.swing.context.Context;

public class ContextOperator {

    public Container getSource() {
        return (Container) Context.getContext().getSource();
    }

    public Component findComponent(String className) {
        return null;
    }

    public List<Component> findAllComponents(String className) {
        return findChildComponentsByClass(getSource(), className);
    }

    /**
     * Find all childComponents of a given type
     */
    private List<Component> findChildComponentsByClass(Component component, String className) {

        List<Component> result = new ArrayList<Component>();

        if (component.getClass().getName().equals(className)) {
            result.add(component);
        } else if (component instanceof Container) {

            Component[] childComponents = ((Container) component).getComponents();
            for (Component child : childComponents) {
                result.addAll(findChildComponentsByClass(child, className));
            }
        }

        return result;
    }
}
