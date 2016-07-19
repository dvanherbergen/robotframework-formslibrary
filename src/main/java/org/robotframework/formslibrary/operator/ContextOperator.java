package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;

public class ContextOperator {

    private boolean useRootContext;

    /**
     * Create operator for the currently selected context or the root context.
     */
    public ContextOperator(boolean useRootContext) {
        this.useRootContext = useRootContext;
    }

    /**
     * Create operator for the currently selected context.
     */
    public ContextOperator() {
        this(false);
    }

    public Container getSource() {
        if (useRootContext) {
            return (Container) FormsContext.getRootContext().getSource();
        } else {
            return (Container) FormsContext.getContext().getSource();
        }
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

            String editable = "";
            if (ComponentUtil.isEditable(component)) {
                editable = " [editable] ";
            }
            String hidden = "";
            if (!component.isShowing()) {
                hidden = " [hidden] ";
            }

            String location = String.format("%1$-8s", component.getX() + "," + component.getY());
            System.out.println(location + " : " + ComponentUtil.getFormattedComponentNames(component) + editable + hidden);
        }
    }

    public void listTextFields() {
        for (Component component : findTextFields()) {

            String editable = "";
            if (ComponentUtil.isEditable(component)) {
                editable = " [editable] ";
            }

            String location = String.format("%1$-8s", component.getX() + "," + component.getY());
            System.out.println(location + " : " + ComponentUtil.getFormattedComponentNames(component) + editable);
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
                + ComponentUtil.getFormattedComponentNames(component) + editable;
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

    private List<Component> findTextFields() {
        List<Component> allFields = findAllComponents(
                new ByClassChooser(-1, ComponentType.TEXT_FIELD, ComponentType.TEXT_AREA, ComponentType.SELECT_FIELD));
        allFields = purgeHiddenFields(allFields);
        allFields = purgeTableFields(allFields);

        return allFields;
    }

    /**
     * Remove all components from the list which are not showing.
     */
    private List<Component> purgeHiddenFields(List<Component> components) {
        List<Component> result = new ArrayList<Component>();
        for (Component component : components) {
            if (component.isShowing()) {
                result.add(component);
            }
        }
        return result;
    }

    /**
     * Remove all fields from the list which are organized in a table layout
     * (same name + same X coordinates)
     */
    private List<Component> purgeTableFields(List<Component> components) {
        List<Component> result = new ArrayList<Component>();
        for (Component component : components) {
            boolean isTableCell = false;

            for (Component otherComponent : components) {
                if (component == otherComponent) {
                    continue;
                }
                if (component.getX() == otherComponent.getX()) {

                    // only take other fields that are really close into account
                    int yDelta = component.getY() - otherComponent.getY();
                    if (-35 < yDelta && yDelta < 35) {
                        String name = "" + ComponentUtil.getAccessibleText(component);
                        if (("" + ComponentUtil.getAccessibleText(otherComponent)).equals(name)) {
                            isTableCell = true;
                            break;
                        }
                    }
                }
            }
            if (!isTableCell) {
                result.add(component);
            }
        }
        return result;
    }

    public Component findTextField(ByNameChooser chooser) {

        for (Component component : findTextFields()) {
            if (chooser.checkComponent(component)) {
                return component;
            }
        }

        return null;
    }

}
