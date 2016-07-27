package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.HorizontalScrollBarOperator;
import org.robotframework.formslibrary.operator.VerticalScrollBarOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ScrollBarKeywords {

    @RobotKeyword("Scroll down in the current window by clicking the down arrow button on a vertical scrollbar.\\n\\n Specify how many times the button should be clicked in the count parameter. One click scrolls approximately one table row. If multiple vertical scrollbars exist in the window, you can optionnally specify the scroll bar index to indicate which scrollbar should be used. Use index 1 for the first scrollbar, 2 for the second, and so on. Example:\\n | Scroll Down | _count_ ||\\\\n | Scroll Down | _count_ | _index_ |\\n")
    @ArgumentNames({ "count", "index=" })
    public void scrollDown(int count, int index) {
        new VerticalScrollBarOperator(index - 1).scrollDown(count);
    }

    @RobotKeywordOverload
    public void scrollDown(int count) {
        scrollDown(count, 1);
    }

    @RobotKeyword("Scroll up in the current window by clicking the up arrow button on a vertical scrollbar.\\n\\n Specify how many times the button should be clicked in the count parameter. One click scrolls approximately one table row. If multiple vertical scrollbars exist in the window, you can optionnally specify the scroll bar index to indicate which scrollbar should be used. Use index 1 for the first scrollbar, 2 for the second, and so on. Example:\\n | Scroll Up | _count_ ||\\\\n | Scroll Up | _count_ | _index_ |\\n")
    @ArgumentNames({ "count", "index=" })
    public void scrollUp(int count, int index) {
        new VerticalScrollBarOperator(index - 1).scrollUp(count);
    }

    @RobotKeywordOverload
    public void scrollUp(int count) {
        scrollUp(count, 1);
    }

    @RobotKeyword("Scroll left in the current window by clicking the left arrow button on a horizontal scrollbar.\\n\\n Specify how many times the button should be clicked in the count parameter. If multiple horizontal scrollbars exist in the window, you can optionnally specify the scroll bar index to indicate which scrollbar should be used. Use index 1 for the first scrollbar, 2 for the second, and so on. Example:\\n | Scroll Left | _count_ ||\\\\n | Scroll Left | _count_ | _index_ |\\n")
    @ArgumentNames({ "count", "index=" })
    public void scrollLeft(int count, int index) {
        new HorizontalScrollBarOperator(index - 1).scrollLeft(count);
    }

    @RobotKeywordOverload
    public void scrollLeft(int count) {
        scrollLeft(count, 1);
    }

    @RobotKeyword("Scroll right in the current window by clicking the right arrow button on a horizontal scrollbar.\\n\\n Specify how many times the button should be clicked in the count parameter. If multiple horizontal scrollbars exist in the window, you can optionnally specify the scroll bar index to indicate which scrollbar should be used. Use index 1 for the first scrollbar, 2 for the second, and so on. Example:\\n | Scroll Right | _count_ ||\\\\n | Scroll Right | _count_ | _index_ |\\n")
    @ArgumentNames({ "count", "index=" })
    public void scrollRight(int count, int index) {
        new HorizontalScrollBarOperator(index - 1).scrollRight(count);
    }

    @RobotKeywordOverload
    public void scrollRight(int count) {
        scrollRight(count, 1);
    }
}
