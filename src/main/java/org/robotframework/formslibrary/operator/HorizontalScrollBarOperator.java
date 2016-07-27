package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Container;

import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.chooser.ByOrientationChooser;
import org.robotframework.formslibrary.chooser.CompositeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for working with horizontal scroll bars.
 */
public class HorizontalScrollBarOperator extends AbstractComponentOperator {

    /**
     * Initialize a HorizontalScrollBarOperator with the n'th horizontal scroll
     * bar in the current context.
     * 
     * @param index
     *            0 (first), 1 (second), ...
     */
    public HorizontalScrollBarOperator(int index) {
        super(new CompositeChooser(new ByOrientationChooser(ByOrientationChooser.Orientation.HORIZONTAL),
                new ByComponentTypeChooser(index, ComponentType.ALL_SCROLL_BAR_TYPES)));
    }

    /**
     * Scroll left by pressing the left arrow button in the scroll bar.
     * 
     * @param count
     *            number of times to press the button.
     */
    public void scrollLeft(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component leftButton = null;
        if (buttons[1].getX() < buttons[0].getX()) {
            leftButton = buttons[1];
        } else {
            leftButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invokeMethod(leftButton, "simulatePush");
        }
    }

    /**
     * Scroll right by pressing the right arrow button in the scroll bar.
     * 
     * @param count
     *            number of times to press the button.
     */
    public void scrollRight(int count) {
        Component[] buttons = ((Container) getSource()).getComponents();
        Component rightButton = null;
        if (buttons[1].getX() > buttons[0].getX()) {
            rightButton = buttons[1];
        } else {
            rightButton = buttons[0];
        }
        for (int i = 0; i < count; i++) {
            ObjectUtil.invokeMethod(rightButton, "simulatePush");
        }
    }
}
