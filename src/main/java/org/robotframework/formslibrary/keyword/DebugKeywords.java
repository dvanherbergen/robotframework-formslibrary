package org.robotframework.formslibrary.keyword;

import java.awt.Container;

import org.robotframework.formslibrary.operator.ComponentIteratorForListing;
import org.robotframework.formslibrary.util.Configuration;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

@RobotKeywords
public class DebugKeywords {

    @RobotKeyword("Prints components (their types and their names) from the selected context.\n\n"
            + "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n" + "Example:\n"
            + "| Select Main Window         |\n" + "| List Fields |\n")
    public String listComponents() {
        ComponentWrapper operator = Context.getContext();
        return ComponentIteratorForListing.getComponentList((Container) operator.getSource()).toString();

    }

    @RobotKeyword("Set a keyword execution delay for all formslibrary keywords.\n\n" + "| Set Delay | _delay in ms_ |\n")
    @ArgumentNames({ "delay" })
    public void setDelay(int delay) {
        Configuration.setKeywordDelay(delay);
    }

    @RobotKeyword("Enable forms library debug output.\n\n" + "| Enable Debug|\n")
    public void enableDebug() {
        Configuration.setDebugEnabled(true);
    }

}
