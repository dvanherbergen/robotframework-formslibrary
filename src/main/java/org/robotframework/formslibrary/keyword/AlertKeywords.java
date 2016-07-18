package org.robotframework.formslibrary.keyword;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.operator.AlertOperator;
import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.TextUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class AlertKeywords {

    @RobotKeyword("Verify that a specific alert message is shown.")
    @ArgumentNames({ "message" })
    public void alertMessageIsShown(String message) {
        String alertMessage = getAlertMessage();
        if (!TextUtil.matches(alertMessage, message)) {
            throw new FormsLibraryException("Alert message '" + alertMessage + "' was not expected.");
        }
    }

    @RobotKeyword("Get the message on an alert dialog. Example:\n\n | ${alertMessage}= | Get Alert Message |\n")
    public String getAlertMessage() {
        return new AlertOperator().getAlertMessage();
    }

    @RobotKeyword("Verify no alert dialog is shown.")
    public void noAlertIsShown() {
        Component alertPane = new ContextOperator().findComponent(ComponentType.ALERT_PANE);
        if (alertPane != null) {
            AlertOperator operator = new AlertOperator(alertPane);
            String alertMessage = operator.getAlertMessage();
            throw new FormsLibraryException("Alert message '" + alertMessage + "' was not expected.");
        }
    }
}
