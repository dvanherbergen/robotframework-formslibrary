package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for working with scrollable list view components.
 */
public class ListViewOperator extends AbstractRootComponentOperator {

    /**
     * Initialize a ListViewOperator with the first list view found in the
     * current context.
     */
    public ListViewOperator() {
        super(new ByComponentTypeChooser(0, ComponentType.LIST_VIEW));
    }

    /**
     * Search for row in the list view by content and select it.
     * 
     * @param columnValues
     *            key column values to search for.
     */
    public void selectRow(String[] columnValues) {

        int rowCount = (Integer) ObjectUtil.invokeMethod(getSource(), "getRowCount");

        for (int i = 0; i < rowCount; i++) {

            boolean foundRow = true;
            for (int j = 0; j < columnValues.length; j++) {
                String cellData = (String) ObjectUtil.invokeMethodWith2IntArgs(getSource(), "getCellData()", j, i);
                Logger.debug("Found list cell [" + j + "," + i + "] : " + cellData);
                if (!TextUtil.matches(cellData, columnValues[j])) {
                    foundRow = false;
                    break;
                }
                Logger.info("Found list cell [" + j + "," + i + "] : " + cellData);
            }

            if (foundRow) {
                ObjectUtil.invokeMethodWithIntArg(getSource(), "setSelectedRow()", i);
                return;
            }
        }

        throw new FormsLibraryException("Could not find row. Maybe it was not visible and you need to scroll down first?");
    }

}
