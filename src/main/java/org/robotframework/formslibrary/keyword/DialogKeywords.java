package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.operator.FormsDialogOperator;
import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class DialogKeywords {

	@RobotKeyword("Select Forms Dialog as current context and sets focus to it. |\n")
	@ArgumentNames({ "identifier" })
	public void selectFormsDialog(String identifier) {

		new FormsDialogOperator(identifier).setAsContext();
	}

	@RobotKeyword("Select Window as current context and sets focus to it. if no identifier is passed the main window is selected. |\n")
	@ArgumentNames({ "identifier=" })
	public void selectFormsWindow(String identifier) {
		FormsContext.setContext(new FrameOperator(identifier));
	}

	@RobotKeywordOverload
	public void selectFormsWindow() {
		selectFormsWindow(null);
	}

}
