package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Configuration;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class DebugKeywords {

    @RobotKeyword("Prints components (their types and their names) from the selected context.\n\n"
            + "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n" + "Example:\n"
            + "| Select Main Window         |\n" + "| List Fields |\n")
    public void listComponents() {
        new ContextOperator().listComponentHierarchy();
    }

    @RobotKeyword("Prints the name of all buttons found in the selected context.\n\n Example:\n | List Buttons|\n")
    public void listButtons() {
        new ContextOperator().listComponents(ComponentType.BUTTON, ComponentType.PUSH_BUTTON);
    }

    @RobotKeyword("Prints the name of all text and dropdown fields found in the selected context.\n\n Example:\n | List Fields|\n")
    public void listFields() {
        new ContextOperator().listComponents(ComponentType.TEXT_AREA, ComponentType.TEXT_FIELD, ComponentType.DROP_DOWN);
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
