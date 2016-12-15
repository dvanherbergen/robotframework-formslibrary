package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.LabelOperator;
import org.robotframework.formslibrary.operator.TextFieldOperatorFactory;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TextFieldKeywords {

	@RobotKeyword("Locate a field by name and set it to the given value. ':' in the field labels are ignored.\n\n" + "Example:\n"
			+ "| Set Field | _username_ | _jeff_ | \n")
	@ArgumentNames({ "identifier", "value" })
	public void setField(String identifier, String value) {
		TextFieldOperatorFactory.getOperator(identifier).setValue(value);
	}

	@RobotKeyword("Locate a field by a label on the same height to the left of the text field. ':' in the field labels are ignored.\n"
			+ "Should only be used for fields which do not have a link with the label\n\n" + "Example:\n"
			+ "| Set Field Next To Label | _username_ | _jeff_ | \n")
	@ArgumentNames({ "identifier", "value" })
	public void setFieldNextToLabel(String identifier, String value) {
		LabelOperator label = new LabelOperator(identifier);
		TextFieldOperatorFactory.getOperator(label).setValue(value);
	}

	@RobotKeyword("Verify field content. This check cannot be used for repeated table fields. For verifying a field in a table use Select Row instead.\n\n"
			+ "Example:\n" + "| Field Should Contain | _username_ | _jeff_ | \n")
	@ArgumentNames({ "identifier", "value" })
	public void verifyField(String identifier, String value) {
		TextFieldOperatorFactory.getOperator(identifier).verifyValue(value);
	}

	@RobotKeyword("Get field content.\n\n" + "Example:\n" + "| \n" + "| ${textFieldValue}= | Get Field | _username_ | \n")
	@ArgumentNames({ "identifier" })
	public String getField(String identifier) {
		return TextFieldOperatorFactory.getOperator(identifier).getValue();
	}

}
