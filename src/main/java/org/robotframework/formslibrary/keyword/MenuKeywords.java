package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.context.ContextChangeMonitor;
import org.robotframework.formslibrary.operator.MenuOperator;
import org.robotframework.formslibrary.util.Constants;
import org.robotframework.formslibrary.util.ContextUtil;
import org.robotframework.formslibrary.util.TextUtil;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class MenuKeywords {

    @RobotKeyword("Select a window menu by its label. Use '>' between different menu levels.\n\n Example:\n | Select Menu | _Help > About This Application_ |\n")
    @ArgumentNames({ "menuPath" })
    public void selectMenu(String menuPath) {

        // restore root context
        ContextUtil.initRootContext();

        // Extract the first menu label from the menu path
        String rootMenuLabel = TextUtil.getFirstSegment(menuPath, Constants.LEVEL_SEPARATOR);

        ContextChangeMonitor monitor = new ContextChangeMonitor();
        monitor.start();
        new MenuOperator(rootMenuLabel).select(menuPath);
        monitor.stop();

    }

}
