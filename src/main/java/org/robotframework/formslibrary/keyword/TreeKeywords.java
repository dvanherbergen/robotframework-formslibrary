package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.TreeOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TreeKeywords {

    @RobotKeyword("Select a navigation tree entry by its label. Use '>' between different levels.\n\n Example:\n"
            + "| Navigate Tree | _root > branch1 > branch1.1_ |\n")
    @ArgumentNames({ "treePath" })
    public void navigateTree(String treePath) {
        // locate the DTree by index walk the menu in the select
        new TreeOperator().select(treePath);
    }

}
