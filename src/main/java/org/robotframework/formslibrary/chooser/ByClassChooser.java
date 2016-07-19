package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

/**
 * Chooser to select forms components based on their name. Hidden components are
 * ignored.
 */
public class ByClassChooser implements ComponentChooser {

    private String[] allowedClassNames;
    private int index;

    /**
     * @param index
     *            of the class to select or -1 to select all classes
     * @param allowedClassNames
     */
    public ByClassChooser(int index, String... allowedClassNames) {
        this.index = index;
        this.allowedClassNames = allowedClassNames;
    }

    @Override
    public boolean checkComponent(Component component) {

        for (String className : allowedClassNames) {
            if (component.getClass().getName().equals(className) && component.isShowing()) {
                if (index <= 0) {
                    return true;
                } else {
                    index--;
                }
            }
        }

        return false;
    }

    @Override
    public String getDescription() {

        return allowedClassNames[0];
    }

}
