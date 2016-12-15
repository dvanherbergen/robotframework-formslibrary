package org.robotframework.formslibrary.keyword;

import javax.swing.JFrame;

import org.robotframework.formslibrary.operator.WFProcessOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.window.FrameOperator;
import org.robotframework.swing.window.FrameOperatorFactory;

import abbot.tester.WindowTester;

@RobotKeywords
public class WFProcessKeywords {

	@RobotKeyword("List All Process Activites\n")
	public void listAllProcessActivities() {

		new WFProcessOperator().listAllProcessActivities();
	}

	@RobotKeyword("List All Process Activities with all properties\n")
	public void listAllProcessActivitiesInfo() {

		new WFProcessOperator().listAllProcessActivitiesInfo();
	}

	@RobotKeyword("Get Process Attributes for the selected activity with all properties\n")
	public Object getProcessAttributes() {
		return new WFProcessOperator().getProcessAttributes();
	}

	@RobotKeyword("Returns a dictionary with all the process activity properties\n")
	@ArgumentNames({ "id" })
	public Object getProcessActivityProperties(String id) {
		return new WFProcessOperator().getProcessActivityProperties(id);
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

	private final IdentifierParsingOperatorFactory<FrameOperator> operatorFactory = new FrameOperatorFactory();

	@RobotKeyword("Closes a frame.\n\n" + "*N.B.* Regular expression can be used to close the window by prefixing the identifier with 'regexp='.\n"
			+ "Example:\n" + "| Close Window | _Help_ |\n" + "| Close Frame | _regexp=^H.*_ | Closes a frame starting with letter H. |\n")
	@ArgumentNames({ "identifier" })
	public void closeFrame(String identifier) {
		FrameOperator operator = operatorFactory.createOperator(identifier);
		operator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		new WindowTester().actionClose(operator.getSource());
	}

}
