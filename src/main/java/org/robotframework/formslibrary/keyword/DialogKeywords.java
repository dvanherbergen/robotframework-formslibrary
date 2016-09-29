package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.FormsDialogOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class DialogKeywords {

	@RobotKeyword("Select Forms Dialog as current context and sets focus to it. |\n")
	@ArgumentNames({ "identifier" })
	public void selectFormsDialog(String identifier) {

		new FormsDialogOperator(identifier).setAsContext();
	}

}
