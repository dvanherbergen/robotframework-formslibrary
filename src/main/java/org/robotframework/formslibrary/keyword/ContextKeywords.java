package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.operator.PanelOperator;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.operator.ComponentWrapper;

@RobotKeywords
public class ContextKeywords {

	@RobotKeyword("Set the context to a panel. The panel is selected by its class name.\n")
	@ArgumentNames({ "type" })
	public void setContextByType(String type) {
		FormsContext.setContext(new PanelOperator(type));
		ComponentWrapper context = FormsContext.getContext();
		Logger.info("Context set to " + context.getSource());
	}

}
