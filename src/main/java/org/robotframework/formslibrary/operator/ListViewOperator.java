package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

public class ListViewOperator extends AbstractRootComponentOperator {

    public ListViewOperator() {
        super(new ByClassChooser(0, ComponentType.LIST_VIEW));
    }

    public void selectRow(String[] columnValues) {

        int rowCount = (Integer) ObjectUtil.invoke(getSource(), "getRowCount");

        for (int i = 0; i < rowCount; i++) {

            boolean foundRow = true;

            for (int j = 0; j < columnValues.length; j++) {

                String cellData = (String) ObjectUtil.invokeWithIntIntArg(getSource(), "getCellData()", j, i);
                Logger.debug("Found list cell [" + j + "," + i + "] : " + cellData);
                if (!TextUtil.matches(cellData, columnValues[j])) {
                    foundRow = false;
                    break;
                }
                Logger.info("Found list cell [" + j + "," + i + "] : " + cellData);
            }

            if (foundRow) {
                ObjectUtil.invokeWithIntArg(getSource(), "setSelectedRow()", i);
                return;
            }
        }

        throw new FormsLibraryException("Could not find row.");

        // ListView v;
        // v.setSelectedRow(arg0);
    }

}
