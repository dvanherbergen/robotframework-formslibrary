package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;

import org.robotframework.formslibrary.chooser.ByClassChooser;
import org.robotframework.formslibrary.chooser.ByOrientationChooser;
import org.robotframework.formslibrary.chooser.CompositeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

public class HorizontalScrollBarOperator extends AbstractComponentOperator {

    public HorizontalScrollBarOperator(int index) {
        super(new CompositeChooser(new ByOrientationChooser(ByOrientationChooser.Orientation.HORIZONTAL),
                new ByClassChooser(index, ComponentType.ALL_SCROLL_BAR_TYPES)));
    }

    public void scrollLeft(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component leftButton = null;
        if (buttons[1].getX() < buttons[0].getX()) {
            leftButton = buttons[1];
        } else {
            leftButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invoke(leftButton, "simulatePush");
        }
    }

    public void scrollRight(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component rightButton = null;
        if (buttons[1].getX() > buttons[0].getX()) {
            rightButton = buttons[1];
        } else {
            rightButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invoke(rightButton, "simulatePush");
        }
    }
}
