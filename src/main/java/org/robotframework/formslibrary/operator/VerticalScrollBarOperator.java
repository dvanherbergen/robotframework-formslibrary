package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;

import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.chooser.ByOrientationChooser;
import org.robotframework.formslibrary.chooser.CompositeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with ComponentType.SCROLL_BAR components.
 */
public class VerticalScrollBarOperator extends AbstractComponentOperator {

    /**
     * Initialize a VerticalScrollBarOperator with the n'th vertical scroll bar
     * in the current context.
     * 
     * @param index
     *            0 (first), 1 (second), ...
     */
    public VerticalScrollBarOperator(int index) {
        super(new CompositeChooser(new ByOrientationChooser(ByOrientationChooser.Orientation.VERTICAL),
                new ByComponentTypeChooser(index, ComponentType.ALL_SCROLL_BAR_TYPES)));
    }

    /**
     * Scroll up by pressing the up arrow button in the scroll bar.
     * 
     * @param count
     *            number of times to press the button.
     */
    public void scrollUp(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component upButton = null;
        if (buttons[1].getY() < buttons[0].getY()) {
            upButton = buttons[1];
        } else {
            upButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invokeMethod(upButton, "simulatePush");
        }
    }

    /**
     * Scroll down by pressing the down arrow button in the scroll bar.
     * 
     * @param count
     *            number of times to press the button.
     */
    public void scrollDown(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component downButton = null;
        if (buttons[1].getY() > buttons[0].getY()) {
            downButton = buttons[1];
        } else {
            downButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invokeMethod(downButton, "simulatePush");
        }
    }
}
