package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

public class TableOperator {

    private Container container;

    public TableOperator(Container container) {
        this.container = container;
    }

    public void selectRow(String[] columnValues) {

        // find all matching first columns
        List<Component> keyColumns = findTextFieldsByValue(container, columnValues[0]);
        if (keyColumns.isEmpty()) {
            Assert.fail("No column found with value " + columnValues[0]);
        }

        for (int i = 1; i < columnValues.length; i++) {

            List<Component> nextColumns = findTextFieldsByValue(container, columnValues[i]);
            List<Component> toRemove = new ArrayList<Component>();
            for (Component col : keyColumns) {
                if (!hasNextColumn(col, nextColumns)) {
                    toRemove.add(col);
                }
            }
            keyColumns.removeAll(toRemove);
        }

        if (keyColumns.size() == 0) {
            Assert.fail("No matching row found.");
        } else if (keyColumns.size() > 1) {
            Logger.info("Multiple rows found. Selecting first one.");
        }

        // select row by simulating a mouse click
        Component keyField = keyColumns.get(0);
        Logger.info("Found matching row @ x" + keyField.getX() + ", y" + keyField.getY());
        ComponentUtil.simulateMouseClick(keyField);

        // check if a checkbox exists to the left of the row and select
        // it if it is available

        Component checkBox = locateCheckBox(keyField);
        if (checkBox != null) {
            new CheckboxOperator(checkBox).check();
        }
    }

    private Component locateCheckBox(Component keyField) {

        List<Component> result = findAllCheckboxes(container);
        for (Component box : result) {

            // check if box is located to the left of the keyfield (max 50px)
            if (keyField.getX() - 50 < box.getX() && box.getX() < keyField.getX()) {

                // check if component is at same level of row
                int deltaY = keyField.getY() - box.getY();
                if (-5 < deltaY && deltaY < 5) {
                    return (Component) ObjectUtil.invoke(box, "getLWCheckBox()");
                }
            }
        }

        return null;
    }

    private List<Component> findAllCheckboxes(Component component) {

        List<Component> result = new ArrayList<Component>();

        if (component.getClass().getName().equals("oracle.forms.ui.VCheckbox")) {
            result.add(component);
        } else if (component instanceof Container) {

            Component[] childComponents = ((Container) component).getComponents();
            for (Component child : childComponents) {
                result.addAll(findAllCheckboxes(child));
            }
        }

        return result;
    }

    private List<Component> findTextFieldsByValue(Component component, String value) {

        List<Component> result = new ArrayList<Component>();

        if (component.getClass().getName().equals("oracle.forms.ui.VTextField")) {
            String text = ObjectUtil.getString(component, "getText()");
            if (TextUtil.matches(value, text)) {
                result.add(component);
            }
        } else if (component instanceof Container) {

            Component[] childComponents = ((Container) component).getComponents();
            for (Component child : childComponents) {
                result.addAll(findTextFieldsByValue(child, value));
            }
        }

        return result;
    }

    private boolean hasNextColumn(Component firstColumn, List<Component> otherColumns) {

        for (Component nextCol : otherColumns) {

            int deltaY = firstColumn.getY() - nextCol.getY();
            if (-5 < deltaY && deltaY < 5 && firstColumn.getX() < nextCol.getX()) {
                // component is not located at same vertical position
                // or component is not right of previous component
                return true;
            }

        }
        return false;
    }

}
