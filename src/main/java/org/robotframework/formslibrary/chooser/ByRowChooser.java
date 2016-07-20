package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;

/**
 * Chooser to select fields based on name, type which are located to the right
 * of a given component.
 */
public class ByRowChooser implements ComponentChooser {

    private Component nearbyComponent;
    private String[] allowedClassNames;
    private String name;

    public ByRowChooser(Component nearbyComponent, String identifier, String... allowedClassNames) {
        this.name = identifier;
        this.allowedClassNames = allowedClassNames;
        this.nearbyComponent = nearbyComponent;
    }

    @Override
    public boolean checkComponent(Component component) {

        for (String className : allowedClassNames) {
            if (component.getClass().getName().equals(className)) {
                if (ComponentUtil.hasName(component, name)) {
                    if (ComponentUtil.areaAlignedVertically(nearbyComponent, component)) {
                        Logger.info(
                                "Found " + component.getClass().getSimpleName() + " '" + ComponentUtil.getFormattedComponentNames(component) + "'");
                        return true;
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
