package org.robotframework.formslibrary.operator;

import java.awt.Container;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class ExtendedFrameOperator extends ContainerOperator implements ComponentWrapper {

    public ExtendedFrameOperator(Container b) {
        super(b);
    }

}
