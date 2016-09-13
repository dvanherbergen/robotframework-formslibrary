package org.robotframework.formslibrary.keyword;

import java.util.List;

import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.formslibrary.operator.OracleEngineOperator;
import org.robotframework.formslibrary.operator.WindowOperator;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class WindowKeywords {

	@RobotKeyword("List Window Titles.\n\nLogs the open window titles.\n\nExample:\n| List Window Titles |\n")
	public List<String> listWindowTitles() {

		List<String> result = new WindowOperator().getWindowTitles();
		for (String title : result) {
			Logger.info("Found window '" + title + "'");
		}
		return result;
	}

	@RobotKeyword("Set the context to a specific window. The window is selected by title. Use 'List Window Titles' to view available titles if needed.\n\n\n\nExample:\n| Set Window Context | _myWindow_ |\n")
	@ArgumentNames({ "identifier" })
	public void setWindowContext(String title) {
		new WindowOperator().setWindowAsContext(title);
	}

	@RobotKeyword("Close all open windows except the one containing the system menu.\n\n\n\nExample:\n| Close Open Windows|\n")
	public void closeOpenWindows() {
		new WindowOperator().closeOpenWindows();
	}

	@RobotKeyword("When the application is started using Java Web Start, this can result in a newer version of the application being downloaded first, which results is an invalid context and requires an application restart.\n\n\n\nExample:\n| Verify Context|\n")
	public boolean verifyContext() {
		return FrameOperator.isFrameInCurrentAppContext();
	}

	@RobotKeyword("Take a screen capture of the Oracle Forms main window. The size of the screenshot is limited to the display resolution.")
	@ArgumentNames("targetDirectory=")
	public String captureWindow(String targetDirectory) {
		return new OracleEngineOperator().capture(targetDirectory);
	}

	@RobotKeyword("Take a full size screen capture of the active dialog in Oracle Forms.")
	@ArgumentNames("targetDirectory=")
	public String captureActiveWindow(String targetDirectory) {
		return new ContextOperator().capture(targetDirectory);
	}

	@RobotKeywordOverload
	public String captureWindow() {
		return captureWindow(null);
	}

	@RobotKeywordOverload
	public String captureActiveWindow() {
		return captureActiveWindow(null);
	}

	@RobotKeyword("Set the size of the Oracle Forms main window. Maximum allowed size may be limited by the underlying OS.")
	@ArgumentNames({ "width", "height" })
	public void resizeWindow(int width, int height) {
		new FrameOperator().setWindowSize(width, height);
	}
}
