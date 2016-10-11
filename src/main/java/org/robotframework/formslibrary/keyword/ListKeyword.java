package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.ListOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ListKeyword {

	@RobotKeyword("Select List Item .\n\nExample:\\n| Select List Item | _1_ | _1_ | \n| Select List Item | _listname_ | 1 | \n"
			+ "\n| Select List Item | _listname_ | _value_ | \\n")
	@ArgumentNames({ "listIdentifier", "valueIdentifier" })
	public void selectListItem(String listIdentifier, String valueIdentifier) {
		new ListOperator(listIdentifier).select(valueIdentifier);
	}

}
