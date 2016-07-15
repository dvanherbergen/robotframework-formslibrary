package org.robotframework.formslibrary.operator;

import java.awt.event.FocusEvent;

import org.junit.Assert;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.chooser.ByPrecedingLWLabelChooser;
import org.robotframework.formslibrary.chooser.CompositeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

public class TextFieldOperator extends BaseComponentOperator {

    public TextFieldOperator(String identifier) {
        super(new CompositeChooser(new ByPrecedingLWLabelChooser(identifier),
                new ByNameChooser(identifier, ComponentType.TEXT_FIELD, ComponentType.TEXT_AREA)));
    }

    @Override
    public boolean isEnabled() {
        return getSource().isEnabled();
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
        if (!value.equals(getValue())) {
            Assert.fail("Field value '" + getValue() + "' does not match " + value);
        }
    }

}
