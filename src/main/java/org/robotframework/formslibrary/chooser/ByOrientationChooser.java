package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

/**
 * Chooser to select Oracle Forms components based based on whether they have a
 * horizontal or vertical orientation.
 */
public class ByOrientationChooser implements ComponentChooser {

    public static enum Orientation {
        HORIZONTAL, VERTICAL
    };

    private Orientation orientation;

    /**
     * Chooser based on component orientation.
     * 
     * @param orientation
     *            ByOrientationChooser.Orientation.HORIZONTAL or
     *            ByOrientationChooser.Orientation.VERTICAL
     */
    public ByOrientationChooser(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean checkComponent(Component component) {

        if (orientation == Orientation.VERTICAL) {
            return component.getWidth() <= component.getHeight();
        } else {
            return component.getWidth() > component.getHeight();
        }
    }

    @Override
    public String getDescription() {
        return orientation.toString();
    }

}
