package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.ListViewOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ListViewKeywords {

    @RobotKeyword("Select a row in a list view table by content.\n\n" + "Example:\n" + "| Select List Row | _market_ | _gas_ | \n")
    @ArgumentNames({ "*columnvalues" })
    public void selectListRow(String... columnValues) {
        new ListViewOperator().selectRow(columnValues);
    }
}
