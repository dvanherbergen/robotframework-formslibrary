package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.chooser.ByPrecedingLabelChooser;
import org.robotframework.formslibrary.util.ComponentType;

/**
 * Operator factory for creating operators for select and text fields.
 */
public class TextFieldOperatorFactory {

    /**
     * Create an operator for the given component.
     * 
     * @return TextFieldOperator or SelectFieldOperator depending on the type of
     *         component that was provided.
     */
    public static TextFieldOperator getOperator(Component component) {

        if (component != null) {
            if (ComponentType.SELECT_FIELD.matches(component)) {
                return new SelectFieldOperator(component);
            } else {
                return new TextFieldOperator(component);
            }
        }

        return null;
    }

    /**
     * Create a field operator for a component with the given name.
     * 
     * @return TextFieldOperator or SelectFieldOperator depending on the type of
     *         component that was found.
     */
    public static TextFieldOperator getOperator(String identifier) {

        ContextOperator operator = new ContextOperator();
        Component component = operator
                .findTextField(new ByNameChooser(identifier, ComponentType.TEXT_FIELD, ComponentType.TEXT_AREA, ComponentType.SELECT_FIELD));
        if (component != null) {
            return getOperator(component);
        }

        return new TextFieldOperator(new ByPrecedingLabelChooser(identifier));
    }

}
