package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.context.ContextChangeMonitor;
import org.robotframework.formslibrary.operator.ButtonOperator;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ButtonKeywords {

	@RobotKeyword("Uses current context to search for a button by its label and when found, pushes it.\n\n "
			+ " If the button opens a new window and detectWindowChange=true, the context will be set to the new window automatically. "
			+ "Similarly if the button closes a window, the context will be reset to the root context. DetectWindowChange defaults to true. Example:\n | Click Button | _OK_ |\n")
	@ArgumentNames({ "identifier", "detectWindowChange=" })
	public void clickButton(String identifier, boolean detectWindowChange) {

		if (detectWindowChange) {
			ContextChangeMonitor monitor = new ContextChangeMonitor();
			monitor.start();
			new ButtonOperator(identifier).push();
			monitor.stop();
		} else {
			new ButtonOperator(identifier).push();
		}
	}

	@RobotKeywordOverload
	public void clickButton(String identifier) {
		clickButton(identifier, true);
	}

	@RobotKeyword("Asynchronously Pushes button")
	@ArgumentNames({ "identifier" })
	public void clickButtonAsync(String identifier) {
		ContextChangeMonitor monitor = new ContextChangeMonitor();
		monitor.start();
		new ButtonOperator(identifier).pushAsync();

	}

	@RobotKeyword("Verify if a button is disabled. If fail argument is set to false this test will not fail Example:\n |  Verify Button Is Disabled | _OK_ |\n")
	@ArgumentNames({ "identifier", "fail=" })
	public boolean verifyButtonIsDisabled(String identifier, boolean fail) {
		boolean enabled = new ButtonOperator(identifier).isEnabled();
		if (fail && enabled) {
			throw new FormsLibraryException("Button is enabled.");
		} else {
			Logger.info("Button is " + (enabled ? "enabled" : "disabled"));
			return !enabled;
		}
	}

	@RobotKeywordOverload
	public boolean verifyButtonIsDisabled(String identifier) {
		return verifyButtonIsDisabled(identifier, true);
	}

	@RobotKeyword("Verify if a button is enabled. If fail argument is set to false this test will not fail. Example:\n |  Verify Button Is Enabled | _OK_ |\n")
	@ArgumentNames({ "identifier", "fail=" })
	public boolean verifyButtonIsEnabled(String identifier, boolean fail) {
		boolean enabled = new ButtonOperator(identifier).isEnabled();
		if (fail && !enabled) {
			throw new FormsLibraryException("Button is disabled.");
		} else {
			Logger.info("Button is " + (enabled ? "enabled" : "disabled"));
			return enabled;
		}
	}

	@RobotKeywordOverload
	public boolean verifyButtonIsEnabled(String identifier) {
		return verifyButtonIsEnabled(identifier, true);
	}
}
