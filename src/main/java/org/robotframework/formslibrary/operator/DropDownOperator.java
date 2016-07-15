package org.robotframework.formslibrary.operator;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import org.junit.Assert;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

public class DropDownOperator extends BaseComponentOperator {

    public DropDownOperator(String identifier) {
        super(new ByNameChooser(identifier, ComponentType.DROP_DOWN));
    }

    @Override
    public boolean isEnabled() {
        return getSource().isEnabled();
    }

    public void setValue(String value) {
        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_GAINED));

        if (value == null || value.trim().length() == 0) {
            value = " ";

            // click down arrow until we find the empty value
            for (int i = 0; i < 100; i++) {
                if (value.equals(getValue())) {
                    break;
                }
                getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_PRESSED, 0, 1, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
                getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_RELEASED, 0, 2, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
            }
        } else {

            for (int i = 0; i < 100; i++) {
                if (value.equals(getValue())) {
                    break;
                }
                getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_PRESSED, 0, 1, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
                getSource().dispatchEvent(new KeyEvent(getSource(), KeyEvent.KEY_RELEASED, 0, 2, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
            }
        }

        if (!value.equals(getValue())) {
            Assert.fail("Could not find value '" + value + "' in the list.");
        }

        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_LOST));
    }

    public String getValue() {
        return ObjectUtil.getString(getSource(), "getSelectedItem()");
    }
}
