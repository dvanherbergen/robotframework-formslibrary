package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentType;

/**
 * Chooser to select Oracle Forms components based on their type (class) and
 * index (occurrence). Hidden components are ignored.
 */
public class ByComponentTypeChooser implements ComponentChooser {

    private ComponentType[] allowedTypes;
    private int index;

    /**
     * @param index
     *            Specifies which occurrence of the component in the context to
     *            select. Use -1 to select all occurrences.
     * @param allowedTypes
     *            Specifies which component types to include.
     */
    public ByComponentTypeChooser(int index, ComponentType... allowedTypes) {
        this.index = index;
        this.allowedTypes = allowedTypes;
    }

    @Override
    public boolean checkComponent(Component component) {

        for (ComponentType type : allowedTypes) {
            if (type.matches(component) && component.isShowing()) {
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
        StringBuilder builder = new StringBuilder();
        for (ComponentType t : allowedTypes) {
            if (builder.length() > 0) {
                builder.append(" / ");
            }
            builder.append(t);
        }
        return builder.toString();
    }

}
