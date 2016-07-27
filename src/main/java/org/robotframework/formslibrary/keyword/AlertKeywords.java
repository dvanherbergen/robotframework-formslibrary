package org.robotframework.formslibrary.keyword;

import java.awt.Component;
import java.util.List;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.operator.AlertOperator;
import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.TextUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class AlertKeywords {

    @RobotKeyword("Verify that a specific alert message is shown in a warning or error dialog.")
    @ArgumentNames({ "message" })
    public void verifyAlertMessage(String message) {
        String alertMessage = getAlertMessage();
        if (!TextUtil.matches(alertMessage, message)) {
            throw new FormsLibraryException("Alert message '" + alertMessage + "' was not expected.");
        } else {
            Logger.info("Found alert '" + alertMessage + "'.");
        }
    }

    @RobotKeyword("Get the message on an alert dialog. Example:\n\n | ${alertMessage}= | Get Alert Message |\n")
    public String getAlertMessage() {
        return new AlertOperator().getAlertMessage();
    }

    @RobotKeyword("Verify no alert dialog is shown.")
    public void verifyNoAlertIsShown() {

        List<Component> panes = new ContextOperator(FormsContext.getRootContext())
                .findComponents(new ByComponentTypeChooser(-1, ComponentType.ALERT_PANE));
        if (panes.isEmpty()) {
            Logger.info("No alert dialog found.");
        } else {
            String alertMessage = new AlertOperator(panes.get(0)).getAlertMessage();
            throw new FormsLibraryException("Alert message '" + alertMessage + "' was not expected.");
        }
    }
}
