package org.robotframework.formslibrary.keyword;

import java.util.List;

import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.formslibrary.operator.WindowOperator;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
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

    @RobotKeyword("When the application is started using Java Web Start, this can result in a newer version of the application being downloaded first, which results is an invalid context and requires an application restart.\n\n\n\nExample:\n| Is Context Invalid|\n")
    public boolean isContextInvalid() {
        return !FrameOperator.isFrameInCurrentAppContext();
    }
}
