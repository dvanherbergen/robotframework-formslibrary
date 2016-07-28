package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.chooser.ByRowChooser;
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

        Logger.debug("Locating row " + TextUtil.concatenateArrayElements(columnValues));

        List<List<Component>> potentialColumnFieldMatches = new ArrayList<List<Component>>();

        for (String keyValue : columnValues) {
            List<Component> matches = findTextFieldsByValue(keyValue);
            potentialColumnFieldMatches.add(matches);
            Logger.debug("Found " + matches.size() + " potential matches for '" + keyValue + "'.");
        }

        List<Component> keyColumns = potentialColumnFieldMatches.get(0);
        if (keyColumns.isEmpty()) {
            throw new FormsLibraryException("No column found with value '" + columnValues[0] + "'");
        }

        // filter out all columns that don't have an adjacent column
        for (int i = potentialColumnFieldMatches.size(); i > 1; i--) {

            List<Component> rightColumns = potentialColumnFieldMatches.get(i - 1);
            List<Component> leftColumns = potentialColumnFieldMatches.get(i - 2);
            List<Component> toRemove = new ArrayList<Component>();

            for (Component col : leftColumns) {
                if (!hasAdjacentColumn(col, rightColumns)) {
                    toRemove.add(col);
                }
            }
            leftColumns.removeAll(toRemove);

        }

        if (keyColumns.size() == 0) {
            throw new FormsLibraryException("No matching row found.");
        } else if (keyColumns.size() > 1) {
            Logger.info("Multiple rows found. Selecting first one.");
        }

        Component firstField = keyColumns.get(0);
        Logger.info("Found matching row @ " + firstField.getX() + ", " + firstField.getY() + ".");
        return firstField;

    }

    /**
     * Select a row by simulating a mouse click in the first field.
     */
    public void selectRow(String[] columnValues) {
        Component firstRowField = findRow(columnValues);
        ComponentUtil.simulateMouseClick(firstRowField);
    }

    /**
     * Find all components of a given type located on the same vertical position
     * as the table text fields.
     */
    private List<Component> findRowComponents(Component keyField, ComponentType... allowedTypes) {

        List<Component> allComponents = findComponents(new ByComponentTypeChooser(-1, allowedTypes));
        List<Component> componentsOnRow = new ArrayList<Component>();

        for (Component component : allComponents) {
            if (ComponentUtil.areAlignedVertically(keyField, component)) {
                componentsOnRow.add(component);
            }
        }

        Collections.sort(componentsOnRow, new ComponentComparator());
        return componentsOnRow;
    }

    private List<Component> findTextFieldsByValue(String value) {

        List<Component> allTextFields = findComponents(new ByComponentTypeChooser(-1, ComponentType.ALL_TEXTFIELD_TYPES));
        List<Component> result = new ArrayList<Component>();

        for (Component textField : allTextFields) {
            TextFieldOperator operator = TextFieldOperatorFactory.getOperator(textField);
            String text = operator.getValue();
            if (TextUtil.matches(text, value)) {
                result.add(textField);
            }
        }

        return result;
    }

    private boolean hasAdjacentColumn(Component firstColumn, List<Component> otherColumns) {

        for (Component nextCol : otherColumns) {
            if (ComponentUtil.areAdjacent(firstColumn, nextCol)) {
                return true;
            }
        }
        return false;
    }

    private CheckboxOperator getCheckboxOperator(int index, String[] columnValues) {

        List<Component> boxes = findRowComponents(findRow(columnValues), ComponentType.CHECK_BOX_WRAPPER);

        if (boxes.size() < index) {
            throw new FormsLibraryException("Only found " + boxes.size() + " checkboxes next to the row");
        }

        return new CheckboxOperator((Component) ObjectUtil.invokeMethod(boxes.get(index - 1), "getLWCheckBox()"));
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

    public void setRowField(String identifier, String value, String[] columnValues) {

        Component firstColumn = findRow(columnValues);
        List<Component> results = findComponents(new ByRowChooser(firstColumn, identifier, ComponentType.ALL_TEXTFIELD_TYPES));

        if (results.isEmpty()) {
            throw new FormsLibraryException("No row field found with name '" + identifier + "'");
        }

        TextFieldOperator operator = TextFieldOperatorFactory.getOperator(results.get(0));
        operator.setValue(value);
    }

    /**
     * Check if a row exists with the given column values.
     */
    public boolean rowExists(String[] columnValues) {
        try {
            Component c = findRow(columnValues);
            if (c != null) {
                return true;
            }
        } catch (FormsLibraryException e) {
            return false;
        }
        return false;
    }

    /**
     * Push on the n'th button in a row identified by column values.
     */
    public void selectRowButton(int index, String[] columnValues) {

        List<Component> buttons = findRowComponents(findRow(columnValues), ComponentType.ALL_BUTTON_TYPES);
        if (buttons.size() < index) {
            throw new FormsLibraryException("Only found " + buttons.size() + " buttons next to the row");
        }
        new ButtonOperator(buttons.get(index - 1)).push();
    }

}
