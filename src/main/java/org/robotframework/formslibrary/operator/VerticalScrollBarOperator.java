package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;

import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.chooser.ByOrientationChooser;
import org.robotframework.formslibrary.chooser.CompositeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

public class VerticalScrollBarOperator extends AbstractComponentOperator {

    public VerticalScrollBarOperator(int index) {
        super(new CompositeChooser(new ByOrientationChooser(ByOrientationChooser.Orientation.VERTICAL),
                new ByClassChooser(index, ComponentType.ALL_SCROLL_BAR_TYPES)));
    }

    public void scrollUp(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component upButton = null;
        if (buttons[1].getY() < buttons[0].getY()) {
            upButton = buttons[1];
        } else {
            upButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invoke(upButton, "simulatePush");
        }
    }

    public void scrollDown(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component downButton = null;
        if (buttons[1].getY() > buttons[0].getY()) {
            downButton = buttons[1];
        } else {
            downButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invoke(downButton, "simulatePush");
        }
    }
}
