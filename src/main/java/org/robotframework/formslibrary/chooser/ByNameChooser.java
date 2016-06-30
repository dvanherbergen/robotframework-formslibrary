package org.robotframework.formslibrary.chooser;

import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Chooser to select forms components based on their name.
 */
public class ByNameChooser implements ComponentChooser {

    private static final Pattern INDEXED_NAME_PATTERN = Pattern.compile("(.*)(\\[)([0-9]*)(\\])");

    private String[] allowedClassNames;
    private String name;
    private int desiredIndex;
    private int currentIndex;

    public ByNameChooser(String identifier, String... allowedClassNames) {
        this.allowedClassNames = allowedClassNames;
        this.name = parseName(identifier);
        this.desiredIndex = parseIndex(identifier);
    }

    private int parseIndex(String identifier) {
        Matcher m = INDEXED_NAME_PATTERN.matcher(identifier);
        if (m.matches()) {
            return Integer.valueOf(m.group(3));
        } else {
            return 0;
        }
    }

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

        for (String className : allowedClassNames) {
            if (component.getClass().getName().equals(className)) {
                String componentName = ComponentUtil.getComponentName(component);
                if (TextUtil.matches(componentName, name)) {
                    Logger.info("Found matching " + className + " with name " + componentName + " and index " + currentIndex);
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
