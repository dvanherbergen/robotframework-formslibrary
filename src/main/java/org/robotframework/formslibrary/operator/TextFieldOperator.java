package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.event.FocusEvent;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

public class TextFieldOperator extends BaseComponentOperator {

    public TextFieldOperator(Component component) {
        super(component);
    }

    public void setValue(String value) {
        // apply focus to the field, otherwise values are ignored in the
        // searches.
        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_GAINED));
        ObjectUtil.invokeMethodWithStringArg(getSource(), "setText()", value);
        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_LOST));
    }

    public String getValue() {
        return ObjectUtil.getString(getSource(), "getText()");
    }

    public void verifyValue(String value) {
        if (!TextUtil.matches(getValue(), value)) {
            throw new FormsLibraryException("Field value '" + getValue() + "' does not match " + value);
        }
    }

}
