package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.chooser.ByPrecedingLWLabelChooser;
import org.robotframework.formslibrary.util.ComponentType;

public class TextFieldOperatorFactory {

    public static TextFieldOperator getOperator(Component component) {

        if (component != null) {
            if (component.getClass().getName().equals(ComponentType.SELECT_FIELD)) {
                return new SelectOperator(component);
            } else {
                return new TextFieldOperator(component);
            }
        }

        return null;
    }

    public static TextFieldOperator getOperator(String identifier) {

        ContextOperator operator = new ContextOperator();
        Component component = operator
                .findTextField(new ByNameChooser(identifier, ComponentType.TEXT_FIELD, ComponentType.TEXT_AREA, ComponentType.SELECT_FIELD));
        if (component != null) {
            return getOperator(component);
        }

        return new TextFieldOperator(new ByPrecedingLWLabelChooser(identifier));
    }

}
