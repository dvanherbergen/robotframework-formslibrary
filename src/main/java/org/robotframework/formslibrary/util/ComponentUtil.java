package org.robotframework.formslibrary.util;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.accessibility.AccessibleContext;

public class ComponentUtil {

    public static String getComponentName(Component component) {

        String componentType = component.getClass().getName();
        String componentName = null;

        if (componentType.equals("oracle.ewt.button.PushButton") || componentType.equals("oracle.ewt.lwAWT.lwMenu.LWMenu")
                || componentType.equals("oracle.forms.ui.ExtendedCheckbox")) {
            componentName = ObjectUtil.getString(component, "getLabel()");
        } else if (componentType.equals("oracle.forms.ui.VButton") || componentType.equals("oracle.forms.ui.VTextField")) {
            componentName = getAccessibleText(component);
        }

        if (componentName == null) {
            componentName = getAccessibleText(component);
        }

        if (componentName == null) {
            componentName = component.getName();
        }

        return componentName;
    }

    private static String getAccessibleText(Component component) {

        AccessibleContext c = component.getAccessibleContext();
        if (c != null) {
            String desc = c.getAccessibleDescription();
            if (desc != null) {
                return TextUtil.removeNewline(desc);
            }
        }
        return null;
    }

    public static boolean isEditable(Component component) {

        String componentType = component.getClass().getName();
        boolean editable = false;

        if (componentType.equals("oracle.forms.ui.VTextField")) {
            editable = ObjectUtil.getBoolean(component, "isEditable()");
        }
        return editable;
    }

    /**
     * Simulate a left mouse click on the given component.
     */
    public static void simulateMouseClick(Component component) {

        component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_PRESSED, 0, MouseEvent.BUTTON1, 5, 5, 1, false));
        component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_RELEASED, 0, MouseEvent.BUTTON1, 5, 5, 1, false));
    }
}
