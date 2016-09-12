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

	@RobotKeyword("Get checkbox value (true is checked, false is unchecked)")
	@ArgumentNames({ "identifier" })
	public boolean getCheckbox(String identifier) {
		return new CheckboxOperator(identifier).isChecked();
	}

	@RobotKeyword("Select checkbox.\n\nExample:\n| Select Checkbox | _checkboxname_ | \n")
	@ArgumentNames({ "identifier" })
	public void selectCheckbox(String identifier) {
		new CheckboxOperator(identifier).check();
	}

	@RobotKeyword("Deselect checkbox.\n\nExample:\n| Deselect Checkbox | _checkboxname_ | \n")
	@ArgumentNames({ "identifier" })
	public void deselectCheckbox(String identifier) {
		new CheckboxOperator(identifier).uncheck();
	}

}
