package org.robotframework.formslibrary.keyword;

import java.awt.Container;

import org.robotframework.formslibrary.operator.TableOperator;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.TextUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

@RobotKeywords
public class TableKeywords {

    @RobotKeyword("Select a row in a result table by content.\n\n" + "Example:\n" + "| Select Row | _market_ | _gas_ | \n")
    @ArgumentNames({ "*columnvalues" })
    public void selectRow(String... columnValues) {
        ComponentWrapper operator = Context.getContext();
        Logger.info("Locating row " + TextUtil.formatArray(columnValues));
        new TableOperator((Container) operator.getSource()).selectRow(columnValues);
    }

}
