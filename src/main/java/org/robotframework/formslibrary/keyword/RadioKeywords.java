package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.RadioOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class RadioKeywords {

    @RobotKeyword("Select Radio Button.\n\nExample:\n| Select Radio Button | _radio option_\n")
    @ArgumentNames({ "identifier" })
    public void selectRadioButton(String identifier) {
        new RadioOperator(identifier).select();
    }

    @RobotKeyword("Get Radio Button selection.\n\nExample:\n| Get Radio Button | _radio option_\n")
    @ArgumentNames({ "identifier" })
    public boolean getRadioButton(String identifier) {
        return new RadioOperator(identifier).isSelected();
    }
}
