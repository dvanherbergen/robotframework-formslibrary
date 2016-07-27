package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.TableOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TableKeywords {

    @RobotKeyword("Select a row in a result table by content.\n\n" + "Example:\n" + "| Select Row | _market_ | _gas_ | \n")
    @ArgumentNames({ "*columnvalues" })
    public void selectRow(String... columnValues) {
        new TableOperator().selectRow(columnValues);
    }

    @RobotKeyword("Set a field value in a table row." + " The row is identified by values\n\n" + "Example:\n"
            + "| Set Row Field | _field name_ || _field value_ | _first col value_ | _second col value_ | \n")
    @ArgumentNames({ "identifier", "value", "*columnvalues" })
    public void setRowField(String identifier, String value, String... columnValues) {
        new TableOperator().setRowField(identifier, value, columnValues);
    }

    @RobotKeyword("Select a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
            + " The row is identified by values\n\n" + "Example:\n"
            + "| Select Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
    @ArgumentNames({ "index", "*columnvalues" })
    public void selectRowCheckbox(int index, String... columnValues) {
        new TableOperator().selectRowCheckbox(index, columnValues);
    }

    @RobotKeyword("Deselect a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
            + " The row is identified by values\n\n" + "Example:\n"
            + "| Select Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
    @ArgumentNames({ "index", "*columnvalues" })
    public void deselectRowCheckbox(int index, String... columnValues) {
        new TableOperator().deselectRowCheckbox(index, columnValues);
    }

    @RobotKeyword("Get the state (true/false) of a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
            + " The row is identified by values\n\n" + "Example:\n"
            + "| ${value}= | Get Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
    @ArgumentNames({ "index", "*columnvalues" })
    public boolean getRowCheckbox(int index, String... columnValues) {
        return new TableOperator().getRowCheckboxState(index, columnValues);
    }
}
