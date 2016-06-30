package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.TabOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TabKeywords {

    @RobotKeyword("Select a tab by name.\n\n Example:\n | Select Tab | _name_ | \n")
    @ArgumentNames({ "tabname" })
    public void selectTab(String name) {
        new TabOperator().select(name);
    }

}
