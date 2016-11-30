package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.DebugUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class DebugKeywords {

	@RobotKeyword("Prints components (their types and their names) from the selected context.\n\n"
			+ "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n" + "Example:\n"
			+ "| Select Main Window         |\n" + "| List Fields |\n")
	public void listComponents() {
		new ContextOperator().listComponentHierarchy();
	}

	@RobotKeyword("Initializes all the missing Component Names in the selected context.\n\n")
	public void initMissingComponentNames() {
		new ContextOperator().initMissingComponentNames();
	}

	@RobotKeyword("Prints all components (their types and their names) available in the application window.\n\n")
	public void listAllComponents() {
		new ContextOperator(FormsContext.getRootContext()).listComponentHierarchy();
	}

	@RobotKeyword("Prints windows\n")
	public void listAllWindows() {
		FormsContext.listWindows();
	}

	@RobotKeyword("Prints the name of all buttons found in the selected context.\n\n Example:\n | List Buttons|\n")
	public void listButtons() {
		new ContextOperator().listComponents(ComponentType.BUTTON, ComponentType.PUSH_BUTTON, ComponentType.LW_BUTTON, ComponentType.CHECK_BOX,
				ComponentType.EXTENDED_CHECKBOX, ComponentType.JBUTTON);
	}

	@RobotKeyword("Prints the name of all text and dropdown fields found in the selected context.\n\n Example:\n | List Fields|\n")
	public void listFields() {
		new ContextOperator().listTextFields();
	}

	@RobotKeyword("Set a keyword execution delay for all formslibrary keywords.\n\n" + "| Set Delay | _delay in ms_ |\n")
	@ArgumentNames({ "delay" })
	public void setDelay(int delay) {
		DebugUtil.setKeywordDelay(delay);
	}

	@RobotKeyword("Enable forms library debug output.\n\n" + "| Enable Debug|\n")
	public void enableDebug() {
		DebugUtil.setDebugEnabled(true);
	}

	@RobotKeyword("Get the current context.\n\n" + "| Get Current Context|\n")
	public String getContext() {
		return ComponentUtil.getFormattedComponentNames(new ContextOperator().getSource());
	}

}
