package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.context.ContextChangeMonitor;
import org.robotframework.formslibrary.operator.ButtonOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ButtonKeywords {

    @RobotKeyword("Uses current context to search for a button by its label and when found, pushes it.\n\n "
            + " If the button opens a new window and detectNewWindow=true, the context will be set to the new window automatically. detectNewWindow defaults to true. Example:\n | Click Button | _OK_ |\n")
    @ArgumentNames({ "identifier", "detectNewWindow=" })
    public void clickButton(String identifier, boolean detectNewWindow) {

        if (detectNewWindow) {
            ContextChangeMonitor monitor = new ContextChangeMonitor();
            monitor.start();
            new ButtonOperator(identifier).push();
            monitor.stop();
        } else {
            new ButtonOperator(identifier).push();
        }
    }

    @RobotKeywordOverload
    public void clickButton(String identifier) {
        clickButton(identifier, true);
    }
}
