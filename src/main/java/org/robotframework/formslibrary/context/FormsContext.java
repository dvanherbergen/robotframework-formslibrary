package org.robotframework.formslibrary.context;

import java.awt.Component;
import java.awt.Window;

import org.robotframework.formslibrary.operator.FrameOperator;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

/**
 * FormsContext holds the currently selected context (Container component) in
 * which all new components are searched. A context can be the root context (the
 * main swing window) or an oracle forms window/dialog.
 */
public class FormsContext {
	private static String FORMS_TITLE = "Oracle Fusion Middleware Forms Services";
	private static ComponentWrapper context;

	private static Boolean isFormServicesApp = null;

	public static void setContext(ComponentWrapper operator) {
		context = operator;
		// TODO remove swing library context dependency if possible
		Context.setContext(context);
	}

	/**
	 * Get current context. If no context is set, it will be defaulted to the
	 * first window.
	 */
	public static ComponentWrapper getContext() {

		// set root context if no context exists
		if (context == null) {
			Logger.debug("No context found.");
			resetContext();
		}

		if (isFormsServicesApp()) {

			Component contextComponent = Context.getContext().getSource();

			// verify that the current window context is still part of the
			// desktop
			if (!new FrameOperator().containsComponent(contextComponent)) {
				Logger.info("Context " + ComponentUtil.getFormattedComponentNames(contextComponent) + " is no longer part of desktop.");
				resetContext();
				return context;
			}

			// verify the type of context
			// if (!ComponentType.JFRAME.matches(contextComponent) &&
			// !ComponentType.EXTENDED_FRAME.matches(contextComponent)
			// && !ComponentType.WINDOW.matches(contextComponent) &&
			// !ComponentType.BUFFERED_FRAME.matches(contextComponent)) {
			// throw new FormsLibraryException("Invalid context selected: " +
			// contextComponent.getClass().getSimpleName());
			// }
		}

		return context;
	}

	/**
	 * @return root context (the forms desktop window)
	 */
	public static ComponentWrapper getRootContext() {
		return new FrameOperator();
	}

	/**
	 * @return root context (the forms desktop window)
	 */
	public static void listWindows() {
		Window[] windows = Window.getWindows();
		for (Window window : windows) {
			Logger.info(
					"Window  - " + window.getName() + " - " + window.getClass().getName() + " title=" + ObjectUtil.getFieldIfExists(window, "title"));
		}
	}

	public static boolean isFormsServicesApp() {
		if (isFormServicesApp == null) {
			isFormServicesApp = checkFormServicesApp();
		}
		return isFormServicesApp;
	}

	private static boolean checkFormServicesApp() {
		Window[] windows = Window.getWindows();
		for (Window window : windows) {
			Object title = ObjectUtil.getFieldIfExists(window, "title");
			if (title != null && title instanceof String) {
				if (((String) title).equals(FORMS_TITLE)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Change the context to the root context.
	 */
	public static void resetContext() {
		Logger.info("Changing context to main desktop window.");
		context = getRootContext();
		Context.setContext(context);
	}
}
