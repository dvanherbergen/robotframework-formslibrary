package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.StatusBarOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class StatusBarKeywords {

    @RobotKeyword("Get the message that is displayed in the status bar at the bottom of the screen. \n\n Example:\n | Get Status Message |\n")
    public String getStatusMessage() {
        return new StatusBarOperator().getMessage();
    }

    @RobotKeyword("Verify that the status bar at the bottom of the screen contains certain content.\n\n" + "Example:\n"
            + "| Status Message Should Contain | _No Record Found_ | \n")
    @ArgumentNames({ "value" })
    public void statusMessageShouldContain(String value) {
        new StatusBarOperator().verifyValue(value);
    }
}
