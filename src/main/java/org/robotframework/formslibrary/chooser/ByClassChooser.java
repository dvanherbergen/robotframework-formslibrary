package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

/**
 * Chooser to select forms components based on their name.
 */
public class ByClassChooser implements ComponentChooser {

    private String[] allowedClassNames;
    private int index;

    public ByClassChooser(int index, String... allowedClassNames) {
        this.index = index;
        this.allowedClassNames = allowedClassNames;
    }

    @Override
    public boolean checkComponent(Component component) {

        for (String className : allowedClassNames) {
            if (component.getClass().getName().equals(className)) {
                if (index == 0) {
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
