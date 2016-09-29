package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.LabelOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class LabelKeywords {

	@RobotKeyword("Get label content.\n\n" + "Example:\n" + "| \n" + "| ${textFieldValue}= | Get Field | _username_ | \n")
	@ArgumentNames({ "identifier" })
	public String getLabel(String identifier) {
		return new LabelOperator(identifier).getValue();
	}

}
