package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;

/**
 * Chooser that select fields based on name and component type and which are
 * located to the right of a given component. Using this chooser, components
 * which are located in a row can be selected.
 */
public class ByRowChooser implements ComponentChooser {

    private Component nearbyComponent;
    private ComponentType[] allowedTypes;
    private String name;

    public ByRowChooser(Component nearbyComponent, String identifier, ComponentType... allowedTypes) {
        this.name = identifier;
        this.allowedTypes = allowedTypes;
        this.nearbyComponent = nearbyComponent;
    }

    @Override
    public boolean checkComponent(Component component) {

        for (ComponentType type : allowedTypes) {
            if (type.matches(component)) {
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
