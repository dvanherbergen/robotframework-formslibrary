package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Oracle Forms default Status Bar operator.
 */
public class StatusBarOperator extends AbstractRootComponentOperator {

    public StatusBarOperator() {
        super(new ByClassChooser(0, ComponentType.STATUS_BAR));
    }

    public String getMessage() {

        Object[] statusBarItems = (Object[]) ObjectUtil.invoke(getSource(), "getItems()");
        String result = "";
        for (int i = 0; i < statusBarItems.length; i++) {
            if (statusBarItems[i].getClass().getName().equals("oracle.ewt.statusBar.StatusBarTextItem")) {
                result = ObjectUtil.getString(statusBarItems[i], "getText()");
                Logger.info("Found status message '" + result + "'");
                break;
            }
        }
        return result;
    }

    public void verifyValue(String value) {
        String message = getMessage();
        if (!TextUtil.matches(message, value)) {
            throw new FormsLibraryException("Status Bar Message '" + message + "' does not match '" + value + "'.");
        }
    }

}
