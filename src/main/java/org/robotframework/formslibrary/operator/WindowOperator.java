package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.ContextUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

/**
 * Operator for oracle forms windows inside a JFrame.
 */
public class WindowOperator extends ContainerOperator implements ComponentWrapper {

    public WindowOperator() {
        super(FrameOperator.newOperatorFor(0), new ByClassChooser(0, "oracle.forms.ui.FormDesktopContainer"));
    }

    public List<String> getWindowTitles() {

        List<String> frameTitles = new ArrayList<String>();
        Component[] extendedFrames = ((Container) getSource()).getComponents();
        for (Component frame : extendedFrames) {
            Component[] children = ((Container) frame).getComponents();
            for (Component child : children) {
                if (child.getClass().getName().equals("oracle.ewt.lwAWT.lwWindow.laf.TitleBar")) {
                    boolean visible = ObjectUtil.getBoolean(frame, "isVisible()");
                    String title = ObjectUtil.getString(child, "getLWWindow().getTitle()");
                    Logger.debug("Found window '" + title + "' [visible=" + visible + "]");
                    if (visible) {
                        frameTitles.add(title);
                    }
                }
            }
        }

        return frameTitles;
    }

    public void setWindowAsContext(String windowTitle) {

        Component[] extendedFrames = ((Container) getSource()).getComponents();
        for (Component frame : extendedFrames) {
            Component[] children = ((Container) frame).getComponents();
            for (Component child : children) {
                if (child.getClass().getName().equals("oracle.ewt.lwAWT.lwWindow.laf.TitleBar")) {

                    String title = ObjectUtil.getString(child, "getLWWindow().getTitle()");
                    if (TextUtil.matches(title, windowTitle)) {

                        Context.setContext(new ExtendedFrameOperator((Container) frame));
                        frame.requestFocus();
                        Logger.info("Context set to window '" + title + "'");
                        return;
                    }
                }
            }
        }

        Assert.fail("No window with title '" + windowTitle + "' found.");
        System.out.println("CONTEXT: " + Context.getContext().getClass());
    }

    public void closeOpenWindows() {

        // reset context before closing windows
        ContextUtil.initRootContext();

        List<Component> framesToClose = new ArrayList<Component>();

        Component[] extendedFrames = ((Container) getSource()).getComponents();
        for (Component frame : extendedFrames) {
            Component[] children = ((Container) frame).getComponents();
            for (Component child : children) {
                if (child.getClass().getName().equals("oracle.ewt.lwAWT.lwWindow.laf.TitleBar")) {
                    if (ObjectUtil.getBoolean(frame, "isVisible()")) {
                        String title = ObjectUtil.getString(child, "getLWWindow().getTitle()");
                        if (!title.contains("Main Menu")) {
                            Logger.info("Closing window '" + title + "'");
                            framesToClose.add(frame);
                        }
                    }
                }
            }
        }

        for (Component frame : framesToClose) {
            ObjectUtil.invoke(frame, "close()");
        }
    }

}
