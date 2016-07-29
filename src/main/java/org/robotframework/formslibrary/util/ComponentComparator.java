package org.robotframework.formslibrary.util;

import java.awt.Component;
import java.awt.Point;
import java.util.Comparator;

/**
 * Comparator to sort components by their location on the screen. Sorting is
 * done from top to bottom and left to right.
 */
public class ComponentComparator implements Comparator<Component> {

    @Override
    public int compare(Component c1, Component c2) {

        Point loc1 = ComponentUtil.getLocationInWindow(c1);
        Point loc2 = ComponentUtil.getLocationInWindow(c2);

        if (loc1.y < loc2.y) {
            return -1;
        } else if (loc1.y == loc2.y) {
            if (loc1.x < loc2.x) {
                return -1;
            } else if (loc1.x == loc2.x) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
