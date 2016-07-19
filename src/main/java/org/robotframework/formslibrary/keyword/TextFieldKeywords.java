package org.robotframework.formslibrary.keyword;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.chooser.ByPrecedingLWLabelChooser;
import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.operator.SelectOperator;
import org.robotframework.formslibrary.operator.TextFieldOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TextFieldKeywords {

    @RobotKeyword("Locate a field by name and set it to the given value. ':' in the field labels are ignored.\n\n" + "Example:\n"
            + "| Set Field | _username_ | _jeff_ | \n")
    @ArgumentNames({ "identifier", "value" })
    public void setField(String identifier, String value) {
        getOperator(identifier).setValue(value);
    }

    @RobotKeyword("Verify field content.\n\n" + "Example:\n" + "| Field Should Contain | _username_ | _jeff_ | \n")
    @ArgumentNames({ "identifier", "value" })
    public void fieldShouldContain(String identifier, String value) {
        getOperator(identifier).verifyValue(value);
    }

    @RobotKeyword("Get field content.\n\n" + "Example:\n" + "| \r\n" + "| ${textFieldValue}= | Get Field | _username_ | \n")
    @ArgumentNames({ "identifier" })
    public String getField(String identifier) {
        return getOperator(identifier).getValue();
    }

    private TextFieldOperator getOperator(String identifier) {

        ContextOperator operator = new ContextOperator();
        Component component = operator
                .findTextField(new ByNameChooser(identifier, ComponentType.TEXT_FIELD, ComponentType.TEXT_AREA, ComponentType.SELECT_FIELD));

        if (component != null) {
            if (component.getClass().getName().equals(ComponentType.SELECT_FIELD)) {
                return new SelectOperator(component);
            } else {
                return new TextFieldOperator(component);
            }
        }

        component = operator.findComponent(new ByPrecedingLWLabelChooser(identifier));
        if (component != null) {
            return new TextFieldOperator(component);
        }

        throw new FormsLibraryException(
                "No component " + identifier + " found in current context '" + ComponentUtil.getFormattedComponentNames(operator.getSource()) + "'");
    }

}
