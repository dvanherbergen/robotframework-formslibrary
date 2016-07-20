package org.robotframework.formslibrary.operator;

import java.awt.Component;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Constants;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator finds top level LWMenu item.
 */
public class MenuOperator extends AbstractRootComponentOperator {

    public MenuOperator(String rootMenuLabel) {
        super(new ByNameChooser(rootMenuLabel, ComponentType.MENU));
    }

    /**
     * Select submenu entries matching the given path
     * 
     * @param path
     *            e.g. level1 > level2 > level3
     */
    public void select(String path) {

        Component menuItem = null;
        int pos = path.indexOf(Constants.LEVEL_SEPARATOR);
        if (pos == -1) {
            menuItem = getSource();
        } else {
            menuItem = findMenuItem(getSource(), TextUtil.getNextSegments(path, Constants.LEVEL_SEPARATOR));
        }
        if (menuItem == null) {
            throw new FormsLibraryException("Could not find menu path '" + path + "'");
        } else {
            Logger.info("Selecting menu '" + path + "'");
            ObjectUtil.invoke(menuItem, "activate()");
        }
    }

    private Component findMenuItem(Component menu, String path) {

        boolean isLastLevel = path.indexOf(Constants.LEVEL_SEPARATOR) == -1;

        String menuLabel = TextUtil.getFirstSegment(path, Constants.LEVEL_SEPARATOR);
        String message = "Error locating menu " + path;

        Component submenu = (Component) ObjectUtil.getNonNullResult(menu, "getSubMenu", message);
        Component content = (Component) ObjectUtil.getNonNullResult(submenu, "getContent", message);
        Component[] components = (Component[]) ObjectUtil.getNonNullResult(content, "getComponents", message);

        for (Component comp : components) {
            String label = ObjectUtil.getString(comp, "getLabel()");
            if (menuLabel.equalsIgnoreCase(label)) {
                if (isLastLevel) {
                    return comp;
                } else {
                    return findMenuItem(comp, TextUtil.getNextSegments(path, Constants.LEVEL_SEPARATOR));
                }
            }
        }

        return null;
    }
}
