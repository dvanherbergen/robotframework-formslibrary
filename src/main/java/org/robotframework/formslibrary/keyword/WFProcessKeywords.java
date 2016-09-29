package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.WFProcessOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class WFProcessKeywords {

	@RobotKeyword("List All Process Activites\n")
	public void listAllProcessActivities() {

		new WFProcessOperator().listAllProcessActivities();
	}

	@RobotKeyword("List All Process Activites with all properties\n")
	public void listAllProcessActivitiesInfo() {

		new WFProcessOperator().listAllProcessActivitiesInfo();
	}

	@RobotKeyword("Select process activity with ID from the Process flow and make it the selected activity.\n")
	@ArgumentNames({ "id" })
	public void selectProcessActivity(String id) {
		new WFProcessOperator().selectActivity(id);
	}

	@RobotKeyword("Selects the base process activity.\n")
	public void selectBaseProcessActivity() {
		new WFProcessOperator().selectActivity(null);
	}

	@RobotKeyword("Take a screen capture of the Oracle Workflow view.")
	@ArgumentNames("targetDirectory=")
	public String captureWorkflowView(String targetDirectory) {
		return new WFProcessOperator().capture(targetDirectory);
	}

	@RobotKeywordOverload
	public String captureWorkflowView() {
		return captureWorkflowView(null);
	}

}
