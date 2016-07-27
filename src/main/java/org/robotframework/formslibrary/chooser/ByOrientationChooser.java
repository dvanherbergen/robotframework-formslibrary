package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Logger;

/**
 * Chooser to select components based on whether they have a horizontal or
 * vertical layout.
 */
public class ByOrientationChooser implements ComponentChooser {

    public static enum Orientation {
        HORIZONTAL, VERTICAL
    };

    private Orientation orientation;

    public ByOrientationChooser(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean checkComponent(Component component) {

        if (component.getClass().getName().equals(ComponentType.SCROLL_BAR)) {
            Logger.info("Found scrollbar " + component.getWidth() + " o " + component.getHeight());
        }

        if (orientation == Orientation.VERTICAL) {
            return component.getWidth() < component.getHeight();
        } else {
            return component.getWidth() > component.getHeight();
        }
    }

    @Override
    public String getDescription() {
        return orientation.toString();
    }

}
