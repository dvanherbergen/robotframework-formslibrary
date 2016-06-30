package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.CheckboxOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class CheckBoxKeywords {

    @RobotKeyword("Verify checkbox is not checked.\n\nExample:\n| Verify Checkbox Is Checked | _checkboxname_ | \n")
    @ArgumentNames({ "identifier" })
    public void verifyCheckboxIsChecked(String identifier) {
        new CheckboxOperator(identifier).verifyChecked();
    }

    @RobotKeyword("Verify checkbox is checked.\n\nExample:\n| Verify Checkbox Is Not Checked | _checkboxname_ | \n")
    @ArgumentNames({ "identifier" })
    public void verifyCheckboxIsNotChecked(String identifier) {
        new CheckboxOperator(identifier).verifyNotChecked();
    }

    @RobotKeyword("Check checkbox.\n\nExample:\n| Check Checkbox | _checkboxname_ | \n")
    @ArgumentNames({ "identifier" })
    public void CheckCheckbox(String identifier) {
        new CheckboxOperator(identifier).check();
    }

    @RobotKeyword("Uncheck checkbox.\n\nExample:\n| Uncheck Checkbox | _checkboxname_ | \n")
    @ArgumentNames({ "identifier" })
    public void UnCheckCheckbox(String identifier) {
        new CheckboxOperator(identifier).uncheck();
    }

}
