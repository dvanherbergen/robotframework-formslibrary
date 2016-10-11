package org.robotframework.formslibrary.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;

import org.robotframework.formslibrary.FormsLibraryException;

/**
 * Utility class for generic interactions with components.
 */
public class ComponentUtil {

	/**
	 * Get component names in format 'name (alternative name)(alternative name)'
	 */
	public static String getFormattedComponentNames(Component component) {

		List<String> names = getComponentNames(component);
		StringBuilder builder = new StringBuilder(names.get(0));
		if (builder.length() == 0) {
			builder.append("${EMPTY}");
		}
		for (int i = 1; i < names.size(); i++) {
			builder.append(" (").append(names.get(i)).append(")");
		}
		return builder.toString();
	}

	/**
	 * Check if one of the component names matches the given name.
	 */
	public static boolean hasName(Component component, String name) {

		if (name == null) {
			return false;
		}

		for (String componentName : getComponentNames(component)) {
			if (TextUtil.matches(componentName, name)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get a list of all valid names for a component. Names can be the label,
	 * accessible text, tooltip text or the default component name.
	 */
	private static List<String> getComponentNames(Component component) {

		List<String> componentNames = new ArrayList<String>();

		if (ComponentType.PUSH_BUTTON.matches(component) || ComponentType.MENU.matches(component) || ComponentType.CHECK_BOX.matches(component)
				|| ComponentType.EXTENDED_CHECKBOX.matches(component) || ComponentType.LW_BUTTON.matches(component)) {
			String label = ObjectUtil.getString(component, "getLabel()");
			if (label != null) {
				componentNames.add(label.trim());
			}
		}

		if (ComponentType.TITLE_BAR.matches(component)) {
			String title = ObjectUtil.getString(component, "getLWWindow().getTitle()");
			if (title != null) {
				componentNames.add(title.trim());
			}
		}

		String accesibleText = getAccessibleText(component);
		if (accesibleText != null && !componentNames.contains(accesibleText)) {
			if (!accesibleText.replaceAll("_", "").toLowerCase().startsWith(component.getClass().getSimpleName().toLowerCase())) {
				componentNames.add(accesibleText.trim());
			}
		}

		String tooltip = getToolTipText(component);
		if (tooltip != null && !componentNames.contains(tooltip)) {
			componentNames.add(tooltip.trim());
		}

		if (componentNames.isEmpty()) {
			// add a blank string so we can select using blank names
			componentNames.add("");
		}
		// add default name
		componentNames.add(component.getName());

		return componentNames;
	}

	/**
	 * Return the location of the component relative to the active window.
	 */
	public static Point getLocationInWindow(Component component) {

		int x = component.getX();
		int y = component.getY();

		Component parent = component.getParent();
		while (parent != null && !ComponentType.EXTENDED_FRAME.matches(parent) && !ComponentType.FORM_DESKTOP.matches(parent)) {
			x = x + parent.getX();
			y = y + parent.getY();
			parent = parent.getParent();
		}

		return new Point(x, y);
	}

	/**
	 * Get the tooltip text for a component.
	 * 
	 * @return text or null if no tooltip is available.
	 */
	private static String getToolTipText(Component component) {

		String text = null;

		try {
			Object toolTip = ObjectUtil.invokeMethod(component, "getToolTipValue()");
			if (toolTip != null) {
				text = (String) ObjectUtil.invokeMethodWithIntArg(toolTip, "getToolTipProperty()", 409);
			}
		} catch (FormsLibraryException e) {
			return null;
		}

		if (text != null && text.trim().length() == 0) {
			return null;
		}

		return text;
	}

	/**
	 * Get the accessible text description for a component.
	 */
	public static String getAccessibleText(Component component) {

		AccessibleContext c = component.getAccessibleContext();
		if (c != null) {
			String desc = c.getAccessibleDescription();
			if (desc != null) {
				return TextUtil.removeNewline(desc);
			}
		}
		return null;
	}

	/**
	 * Check if the component is editable (in case of a text field).
	 */
	public static boolean isEditable(Component component) {

		boolean editable = false;
		if (ComponentType.TEXT_FIELD.matches(component) || ComponentType.TEXT_AREA.matches(component)) {
			editable = ObjectUtil.getBoolean(component, "isEditable()");
		}
		return editable;
	}

	/**
	 * Simulate a left mouse click on the given component.
	 */
	public static void simulateMouseClick(Component component) {

		component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_PRESSED, 0, MouseEvent.BUTTON1, 5, 5, 1, false));
		component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_RELEASED, 0, MouseEvent.BUTTON1, 5, 5, 1, false));
	}

	/**
	 * Simulate a key press/release event on the component.
	 * 
	 * @param key
	 *            KeyEvent.VK_...
	 */
	public static void simulateKeyPressed(Component component, int key) {
		component.dispatchEvent(new KeyEvent(component, KeyEvent.KEY_PRESSED, 0, 1, key, KeyEvent.CHAR_UNDEFINED));
		component.dispatchEvent(new KeyEvent(component, KeyEvent.KEY_RELEASED, 0, 2, key, KeyEvent.CHAR_UNDEFINED));
	}

	/**
	 * Check if a component contains another component in its child components.
	 */
	public static boolean containsComponent(Component parent, Component component) {
		if (parent == component) {
			return true;
		} else if (parent instanceof Container) {
			Component[] childComponents = ((Container) parent).getComponents();
			for (Component child : childComponents) {
				if (containsComponent(child, component)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if comp2 is located to the right of comp1.
	 */
	public static boolean areAdjacent(Component comp1, Component comp2) {

		Point point1 = getLocationInWindow(comp1);
		Point point2 = getLocationInWindow(comp2);

		if (areAlignedVertically(point1, point2)) {
			int deltaX = point2.x - (point1.x + comp1.getWidth());
			if (-3 < deltaX && deltaX < 15) {
				Logger.info("Found adjacent field " + point1.x + "-" + (point1.x + comp1.getWidth()) + "," + point1.y + " / " + point2.x + ","
						+ point2.y + ".");
				return true;
			}
		}
		Logger.debug("No match " + point1.x + "-" + (point1.x + comp1.getWidth()) + "," + point1.y + " / " + point2.x + "," + point2.y + ".");
		return false;
	}

	/**
	 * Check if comp1 is on the same vertical level in the UI as comp2.
	 */
	public static boolean areAlignedVertically(Component comp1, Component comp2) {
		return areAlignedVertically(getLocationInWindow(comp1), getLocationInWindow(comp2));
	}

	/**
	 * Check if point 1 is on the same vertical level in the UI as point 2.
	 */
	private static boolean areAlignedVertically(Point p1, Point p2) {

		int deltaY = p1.y - p2.y;
		if (-3 < deltaY && deltaY < 3) {
			return true;
		}
		return false;
	}

	public static String captureToFile(String targetDirectory, Component component) {

		if (targetDirectory == null) {
			targetDirectory = "";
		}

		File targetFile = getNextAvailableFile(targetDirectory, "screenshot", "png");

		Logger.info("Creating screenshot file " + targetFile.getAbsolutePath());

		Rectangle rect = component.getBounds();

		try {
			targetFile.mkdirs();
			targetFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FormsLibraryException("Error creating file " + targetFile.getAbsolutePath());
		}

		try {
			BufferedImage captureImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
			component.paint(captureImage.getGraphics());
			ImageIO.write(captureImage, "png", targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FormsLibraryException(e);
		}

		String relPath = targetDirectory;
		if (relPath.length() != 0) {
			relPath = relPath + "/";
		}
		return relPath + targetFile.getName();
	}

	private static File getNextAvailableFile(String directory, String basename, String extension) {

		String basepath = getBasePath(directory);
		if (!basepath.endsWith(System.getProperty("file.separator"))) {
			basepath += System.getProperty("file.separator");
		}
		for (int i = 1; i < 1000000; i++) {

			File file = new File(basepath + basename + "-" + i + "." + extension);
			if (!file.exists()) {
				return file;
			}
		}

		throw new FormsLibraryException("No more available screenshot names. Maybe it is time to clean up your system?");
	}

	private static String getBasePath(String directory) {
		String outputDir = System.getProperty("robot.output_dir");
		if (directory == null || directory.length() == 0) {
			return outputDir;
		} else if (new File(directory).isAbsolute()) {
			return directory;
		} else {
			return outputDir + System.getProperty("file.separator") + directory;
		}
	}

	public static String getValue(Component component) {
		if (ComponentType.LABEL.matches(component)) {
			String value = ObjectUtil.getString(component, "getText()");
			if (value != null) {
				return " -> " + value;
			}
		}
		return "";
	}
}
