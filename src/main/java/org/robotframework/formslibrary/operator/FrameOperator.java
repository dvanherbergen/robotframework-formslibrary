package org.robotframework.formslibrary.operator;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.swing.chooser.ByNameOrTitleFrameChooser;
import org.robotframework.swing.common.Identifier;
import org.robotframework.swing.operator.ComponentWrapper;

public class FrameOperator extends JFrameOperator implements ComponentWrapper {

    public static FrameOperator newOperatorFor(int index) {

        return new FrameOperator(index);
    }

    private FrameOperator(int index) {
        super(index);
    }

    public static FrameOperator newOperatorFor(String titleOrName) {

        Identifier identifier = new Identifier(titleOrName);
        if (identifier.isRegExp())
            return new FrameOperator(createRegExpChooser(identifier.asString()));
        return new FrameOperator(new ByNameOrTitleFrameChooser(titleOrName, "Frame"));
    }

    private static ComponentChooser createRegExpChooser(String title) {

        return new JFrameFinder(new FrameByTitleFinder(title, new RegExComparator()));
    }

    private FrameOperator(ComponentChooser chooser) {
        super(chooser);
    }

    private FrameOperator(String title) {
        super(title);
    }

}
