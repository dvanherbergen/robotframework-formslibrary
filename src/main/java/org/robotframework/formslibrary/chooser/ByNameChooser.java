package org.robotframework.formslibrary.chooser;

import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;

/**
 * Chooser to select Oracle Forms components based on their name and type
 * (class). Hidden components are ignored. The name of a component can be the
 * component name, ToolTip text or accessible context description.
 */
public class ByNameChooser implements ComponentChooser {

	private static final Pattern INDEXED_NAME_PATTERN = Pattern.compile("(.*)(\\[)([0-9]*)(\\])");

	private ComponentType[] allowedTypes;
	private String name;
	private int desiredIndex;
	private int currentIndex = 1;

	/**
	 * @param identifier
	 *            name of the component. If multiple components with the same
	 *            name exist, a suffix with the index can be added to the
	 *            identifier. E.g use duplicatedname[1] to indicate the first
	 *            occurrence, duplicatedname[2] for the second, and so on...
	 * @param allowedTypes
	 *            Specifies which component types to include.
	 */
	public ByNameChooser(String identifier, ComponentType... allowedTypes) {
		this.allowedTypes = allowedTypes;
		this.name = parseName(identifier);
		this.desiredIndex = parseIndex(identifier);
	}

	/**
	 * Extract an index from the identifier if one is available.
	 * 
	 * @param identifier
	 *            Identifier in the format NAME or NAME[i].
	 * @return index or 1 if none was found in the identifier.
	 */
	private int parseIndex(String identifier) {
		Matcher m = INDEXED_NAME_PATTERN.matcher(identifier);
		if (m.matches()) {
			return Integer.valueOf(m.group(3));
		} else {
			return 1;
		}
	}

	/**
	 * Extract an name from the identifier.
	 * 
	 * @param identifier
	 *            Identifier in the format NAME or NAME[i].
	 * @return NAME
	 */
	private String parseName(String identifier) {
		Matcher m = INDEXED_NAME_PATTERN.matcher(identifier);
		if (m.matches()) {
			return m.group(1);
		} else {
			return identifier.trim();
		}
	}

	@Override
	public boolean checkComponent(Component component) {
		if (allowedTypes == null || allowedTypes.length == 0) {
			return ComponentUtil.hasName(component, name);
		}

		for (ComponentType type : allowedTypes) {
			if (type.matches(component)) {
				Logger.debug("Checking component " + ComponentUtil.getFormattedComponentNames(component));
				if (ComponentUtil.hasName(component, name)) {
					Logger.info("Found " + component.getClass().getSimpleName() + " '" + ComponentUtil.getFormattedComponentNames(component) + "' ["
							+ currentIndex + "].");
					if (currentIndex == desiredIndex) {
						return true;
					} else {
						currentIndex++;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return name;
	}

}
