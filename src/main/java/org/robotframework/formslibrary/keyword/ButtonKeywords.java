package org.robotframework.formslibrary.keyword;

import org.junit.Assert;
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
            + " If the button opens a new window and detectNewWindow=true, the context will be set to the new window automatically. detectNewWindow defaults to true. Example:\n | Click Button | _OK_ |\n")
    @ArgumentNames({ "identifier", "detectNewWindow=" })
    public void clickButton(String identifier, boolean detectNewWindow) {

        if (detectNewWindow) {
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

    @RobotKeyword("Verify if a button is disabled. Example:\n |  Verify Button Is Disabled | _OK_ |\n")
    @ArgumentNames({ "identifier" })
    public void verifyButtonIsDisabled(String identifier) {
        if (new ButtonOperator(identifier).isEnabled()) {
            Assert.fail("Button is enabled.");
        } else {
            Logger.info("Button is disabled.");
        }
    }

    @RobotKeyword("Verify if a button is enabled. Example:\n |  Verify Button Is Enabled | _OK_ |\n")
    @ArgumentNames({ "identifier" })
    public void verifyButtonIsEnabled(String identifier) {
        if (!new ButtonOperator(identifier).isEnabled()) {
            Assert.fail("Button is disabled.");
        } else {
            Logger.info("Button is enabled.");
        }
    }
}
