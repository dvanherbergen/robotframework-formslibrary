package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

/**
 * Composite chooser. Allows to combine multiple choosers in an 'AND' fashion:
 * all embedded choosers must match in order for the component to be selected.
 */
public class CompositeChooser implements ComponentChooser {

    private ComponentChooser[] choosers;

    /**
     * Create new combination chooser.
     * 
     * @param choosers
     *            one or more choosers.
     */
    public CompositeChooser(ComponentChooser... choosers) {
        this.choosers = choosers;
    }

    @Override
    public boolean checkComponent(Component comp) {

        for (ComponentChooser chooser : choosers) {
            if (!chooser.checkComponent(comp)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder();
        for (ComponentChooser c : choosers) {
            if (builder.length() > 0) {
                builder.append(" / ");
            }
            builder.append(c.getDescription());
        }
        return builder.toString();
    }

}
