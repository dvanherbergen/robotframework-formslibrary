package org.robotframework.formslibrary.operator;

import org.junit.Assert;
import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.util.Constants;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;
import org.robotframework.formslibrary.util.TextUtil;

/**
 * Operator for handling DTree navigation
 */
public class TreeOperator extends BaseComponentOperator {

    public TreeOperator() {
        super(new ByClassChooser(0, "oracle.ewt.dTree.DTree"));
    }

    /**
     * Select entries matching the given path
     * 
     * @param path
     *            e.g. level1 > level2 > level3
     */
    public void select(String path) {

        Object dTreeRootItem = ObjectUtil.invoke(getSource(), "getRoot()");
        Object treeItem = findTreeItem(dTreeRootItem, path);
        if (treeItem == null) {
            Assert.fail("Could not find tree path " + path);
        }

        Object tree = ObjectUtil.invoke(dTreeRootItem, "getTree()");
        Object selection = ObjectUtil.invoke(tree, "getSelection()");
        ObjectUtil.invokeMethodWithTypedArg(selection, "selectItem()", "oracle.ewt.dTree.DTreeItem", treeItem);

    }

    private Object findTreeItem(Object parentItem, String path) {

        boolean isLastLevel = path.indexOf(Constants.LEVEL_SEPARATOR) == -1;
        String labelToSelect = TextUtil.getFirstSegment(path, Constants.LEVEL_SEPARATOR);

        int itemCount = (Integer) ObjectUtil.invoke(parentItem, "getItemCount()");
        for (int i = 0; i < itemCount; i++) {

            Object treeItem = ObjectUtil.invokeWithIntArg(parentItem, "getItem()", i);
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
