package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.Constants;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for handling DTree navigation
 */
public class TreeOperator extends AbstractComponentOperator {

    /**
     * Initialize a TreeOperator with the navigation tree found in the current
     * context.
     */
    public TreeOperator() {
        super(new ByComponentTypeChooser(0, ComponentType.TREE));
    }

    /**
     * Select entries matching the given path
     * 
     * @param path
     *            e.g. level1 > level2 > level3
     */
    public void select(String path) {

        Object dTreeRootItem = ObjectUtil.invokeMethod(getSource(), "getRoot()");
        Object treeItem = findTreeItem(dTreeRootItem, path);
        if (treeItem == null) {
            throw new FormsLibraryException("Could not find tree path " + path);
        }

        Object tree = ObjectUtil.invokeMethod(dTreeRootItem, "getTree()");
        Object selection = ObjectUtil.invokeMethod(tree, "getSelection()");
        ObjectUtil.invokeMethodWithTypedArg(selection, "selectItem()", ComponentType.TREE_ITEM.toString(), treeItem);

    }

    private Object findTreeItem(Object parentItem, String path) {

        boolean isLastLevel = path.indexOf(Constants.LEVEL_SEPARATOR) == -1;
        String labelToSelect = TextUtil.getFirstSegment(path, Constants.LEVEL_SEPARATOR);

        int itemCount = (Integer) ObjectUtil.invokeMethod(parentItem, "getItemCount()");
        for (int i = 0; i < itemCount; i++) {

            Object treeItem = ObjectUtil.invokeMethodWithIntArg(parentItem, "getItem()", i);
            String itemLabel = ObjectUtil.getString(treeItem, "getLabel()");
            boolean isExpanded = ObjectUtil.getBoolean(treeItem, "isExpanded()");
            Logger.debug("Found tree item '" + itemLabel + "' (expanded=" + isExpanded + ")");

            if (!TextUtil.matches(itemLabel, labelToSelect)) {
                continue;
            }

            if (isLastLevel) {
                return treeItem;
            } else {
                if (!isExpanded) {
                    ObjectUtil.invokeMethodWithBoolArg(treeItem, "setExpanded()", true);
                    isExpanded = ObjectUtil.getBoolean(treeItem, "isExpanded()");
                    Logger.debug("Tree item '" + itemLabel + "' (expanded=" + isExpanded + ")");
                    getSource().invalidate();
                }
                return findTreeItem(treeItem, TextUtil.getNextSegments(path, Constants.LEVEL_SEPARATOR));
            }
        }

        return null;
    }

}
