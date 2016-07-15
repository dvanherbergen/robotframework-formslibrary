package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.TextFieldOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TextFieldKeywords {

    @RobotKeyword("Locate a field by name and enter the given text into it. ':' in the field labels are ignored.\n\n" + "Example:\n"
            + "| Set Field | _username_ | _jeff_ | \n")
    @ArgumentNames({ "identifier", "value" })
    public void setField(String identifier, String value) {
        new TextFieldOperator(identifier).setValue(value);
    }

    @RobotKeyword("Verify field content.\n\n" + "Example:\n" + "| Field Should Contain | _username_ | _jeff_ | \n")
    @ArgumentNames({ "identifier", "value" })
    public void fieldShouldContain(String identifier, String value) {
        new TextFieldOperator(identifier).verifyValue(value);
    }

    @RobotKeyword("Get field content.\n\n" + "Example:\n" + "| \r\n" + "| ${textFieldValue}= | Get Field | _username_ | \n")
    @ArgumentNames({ "identifier" })
    public String getField(String identifier) {
        return new TextFieldOperator(identifier).getValue();
    }
}
