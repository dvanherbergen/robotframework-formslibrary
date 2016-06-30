package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.DropDownOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class DropDownKeywords {

    @RobotKeyword("Locate a dropdown field by name and enter the given text into it. ':' in the field labels are ignored.\n\n"
            + "Example:\n | Set Dropdown Field | _market_ | _gas_ | \n")
    @ArgumentNames({ "identifier", "value" })
    public void setDropdownField(String identifier, String value) {
        new DropDownOperator(identifier).setValue(value);
    }

}
