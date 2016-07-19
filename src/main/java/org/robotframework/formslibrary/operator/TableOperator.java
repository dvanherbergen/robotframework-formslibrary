package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.util.ComponentComparator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

public class TableOperator extends ContextOperator {

    /**
     * Locate a matching row by field values.
     * 
     * @return the first field of the matching row.
     */
    private Component findRow(String[] columnValues) {

        Logger.info("Locating row " + TextUtil.formatArray(columnValues));

        // find all matching first columns
        List<Component> keyColumns = findTextFieldsByValue(columnValues[0]);
        if (keyColumns.isEmpty()) {
            throw new FormsLibraryException("No column found with value " + columnValues[0]);
        }

        for (int i = 1; i < columnValues.length; i++) {

            List<Component> nextColumns = findTextFieldsByValue(columnValues[i]);
            List<Component> toRemove = new ArrayList<Component>();
            for (Component col : keyColumns) {
                if (!hasNextColumn(col, nextColumns)) {
                    toRemove.add(col);
                }
            }
            keyColumns.removeAll(toRemove);
        }

        if (keyColumns.size() == 0) {
            throw new FormsLibraryException("No matching row found.");
        } else if (keyColumns.size() > 1) {
            Logger.info("Multiple rows found. Selecting first one.");
        }

        return keyColumns.get(0);

    }

    /**
     * Select a row by simulating a mouse click in the first field.
     */
    public void selectRow(String[] columnValues) {

        Component firstRowField = findRow(columnValues);

        Logger.info("Found matching row @ x" + firstRowField.getX() + ", y" + firstRowField.getY());
        ComponentUtil.simulateMouseClick(firstRowField);

    }

    /**
     * Find all checkboxes located on the same vertical position as the table
     * text fields.
     */
    private List<Component> findRowCheckBoxes(Component keyField) {

        List<Component> result = findAllComponents(ComponentType.CHECK_BOX);
        List<Component> rowCheckboxes = new ArrayList<Component>();

        for (Component box : result) {

            // check if component is at same level of row
            int deltaY = keyField.getY() - box.getY();
            if (-5 < deltaY && deltaY < 5) {
                rowCheckboxes.add(box);
            }
        }

        Collections.sort(rowCheckboxes, new ComponentComparator());
        return rowCheckboxes;
    }

    private List<Component> findTextFieldsByValue(String value) {

        List<Component> allTextFields = findAllComponents(ComponentType.TEXT_FIELD);
        List<Component> result = new ArrayList<Component>();

        for (Component textField : allTextFields) {
            String text = ObjectUtil.getString(textField, "getText()");
            if (TextUtil.matches(value, text)) {
                result.add(textField);
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

    private CheckboxOperator getCheckboxOperator(int index, String[] columnValues) {

        List<Component> boxes = findRowCheckBoxes(findRow(columnValues));

        if (boxes.size() < index) {
            throw new FormsLibraryException("Only found " + boxes.size() + " checkboxes next to the row");
        }

        return new CheckboxOperator((Component) ObjectUtil.invoke(boxes.get(index - 1), "getLWCheckBox()"));
    }

    public void selectRowCheckbox(int index, String[] columnValues) {
        getCheckboxOperator(index, columnValues).check();
    }

    public void deselectRowCheckbox(int index, String[] columnValues) {
        getCheckboxOperator(index, columnValues).uncheck();
    }

    public boolean getRowCheckboxState(int index, String[] columnValues) {
        return getCheckboxOperator(index, columnValues).isChecked();
    }

}
