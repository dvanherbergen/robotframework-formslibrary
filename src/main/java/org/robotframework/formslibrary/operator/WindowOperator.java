package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for oracle forms windows inside a JFrame.
 */
public class WindowOperator extends AbstractRootComponentOperator {

	/**
	 * Initialize a window operator with the main oracle forms desktop window.
	 */
	public WindowOperator() {
		super(new ByComponentTypeChooser(0, ComponentType.FORM_DESKTOP));
	}

	/**
	 * Retrieve a list of all window titles in the root context.
	 */
	public List<String> getWindowTitles() {

		List<String> frameTitles = new ArrayList<String>();
		Component[] extendedFrames = ((Container) getSource()).getComponents();
		for (Component frame : extendedFrames) {
			Component[] children = ((Container) frame).getComponents();
			for (Component child : children) {
				if (ComponentType.TITLE_BAR.matches(child)) {
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

	/**
	 * Change the current context to the window with the given title.
	 * 
	 * @param windowTitle
	 *            title of the window.
	 */
	public void setWindowAsContext(String windowTitle) {

		Component[] extendedFrames = ((Container) getSource()).getComponents();
		for (Component frame : extendedFrames) {
			Component[] children = ((Container) frame).getComponents();
			for (Component child : children) {
				if (ComponentType.TITLE_BAR.matches(child)) {

					String title = ObjectUtil.getString(child, "getLWWindow().getTitle()");
					if (TextUtil.matches(title, windowTitle)) {
						FormsContext.setContext(new FrameOperator((Container) frame));
						frame.requestFocus();
						Logger.info("Context set to window '" + title + "'");
						return;
					}
				}
			}
		}

		throw new FormsLibraryException("No window with title '" + windowTitle + "' found.");
	}

	/**
	 * Close all open windows in the application except the main menu.
	 */
	public void closeOpenWindows() {

		// reset context before closing windows
		FormsContext.resetContext();

		List<Component> framesToClose = new ArrayList<Component>();

		Component[] extendedFrames = ((Container) getSource()).getComponents();
		for (Component frame : extendedFrames) {
			Component[] children = ((Container) frame).getComponents();
			for (Component child : children) {
				if (ComponentType.TITLE_BAR.matches(child)) {
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
			ObjectUtil.invokeMethod(frame, "close()");
		}
	}

}
