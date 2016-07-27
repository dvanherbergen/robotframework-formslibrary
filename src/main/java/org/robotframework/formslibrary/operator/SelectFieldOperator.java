package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for working with drop down select fields.
 */
public class SelectFieldOperator extends TextFieldOperator {

    private Set<String> options = new HashSet<String>();

    /**
     * Initialize a SelectOperator for the given component.
     */
    public SelectFieldOperator(Component component) {
        super(component);
    }

    @Override
    public void setValue(String value) {
        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_GAINED));

        if (value == null || value.trim().length() == 0) {
            value = " ";
        }

        String selection = findSelection(value, KeyEvent.VK_DOWN);
        if (selection == null) {
            selection = findSelection(value, KeyEvent.VK_UP);
        }
        if (selection == null) {
            throw new FormsLibraryException("Could not find value '" + value + "' in the list : " + TextUtil.concatenateSetValues(options));
        }

        getSource().dispatchEvent(new FocusEvent(getSource(), FocusEvent.FOCUS_LOST));

        // verify that we were able to set the field correctly
        verifyValue(value);
    }

    /**
     * Find the dropdown option by simulating arrow up/down until the value is
     * found.
     */
    private String findSelection(String value, int arrowKey) {

        // click arrow until we find the matching value or
        // hit the end of the list
        while (true) {
            String currentSelection = getValue();
            options.add(currentSelection);
            if (TextUtil.matches(currentSelection, value)) {
                return currentSelection;
            }

            getSource().requestFocus();
            ComponentUtil.simulateKeyPressed(getSource(), arrowKey);

            String newSelection = getValue();
            options.add(newSelection);
            if (currentSelection.equals(newSelection)) {
                // nothing changed, we reached the end of the list
                break;
            }
        }

        return null;
    }

    @Override
    public String getValue() {
        return ObjectUtil.getString(getSource(), "getSelectedItem()");
    }

}
