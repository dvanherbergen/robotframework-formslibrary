package org.robotframework.formslibrary.util;

/**
 * Enum for all the different Oracle Forms Components supported by FormsLibrary.
 */
public enum ComponentType {

	// @formatter:off
    CHECK_BOX_WRAPPER("oracle.forms.ui.VCheckbox"),
    CHECK_BOX("oracle.ewt.lwAWT.LWCheckbox"),
    LWList("oracle.ewt.lwAWT.LWList"),
    TEXT_FIELD("oracle.forms.ui.VTextField"),
    ALERT_PANE("oracle.ewt.alert.AlertPane"),
    LABEL("oracle.ewt.lwAWT.LWLabel"),
    DIALOG("oracle.apps.fnd.wf.LWCommonDialog"),
    BUTTON("oracle.forms.ui.VButton"),
    PUSH_BUTTON("oracle.ewt.button.PushButton"),
    MENU("oracle.ewt.lwAWT.lwMenu.LWMenu"),
    EXTENDED_CHECKBOX("oracle.forms.ui.ExtendedCheckbox"),
    EXTENDED_FRAME("oracle.forms.ui.ExtendedFrame"),
    JFRAME("javax.swing.JFrame"),
    TITLE_BAR("oracle.ewt.lwAWT.lwWindow.laf.TitleBar"),
    FORM_DESKTOP("oracle.forms.ui.FormDesktopContainer"),
    TAB_BAR("oracle.ewt.tabBar.TabBar"),
    SELECT_FIELD("oracle.forms.ui.VPopList"),
    TREE("oracle.ewt.dTree.DTree"),
    TREE_ITEM("oracle.ewt.dTree.DTreeItem"),
    TEXT_AREA("oracle.forms.ui.FLWTextArea"),
    WINDOW("oracle.forms.ui.FWindow"),
    LIST_VIEW("oracle.forms.ui.ListView"),
    STATUS_BAR("oracle.ewt.statusBar.StatusBar"),
    STATUS_BAR_TEXT_ITEM("oracle.ewt.statusBar.StatusBarTextItem"),
    SCROLL_BAR("oracle.ewt.lwAWT.LWScrollbar"),
    SCROLL_BAR_BOX("oracle.ewt.scrolling.scrollBox.EwtLWScrollbar"), 
    ORACLE_MAIN("oracle.forms.engine.Main"),
    WFPROCESS("oracle.apps.fnd.wf.WFProcess"),
    LW_BUTTON("oracle.ewt.lwAWT.LWButton");
	
    // @formatter:on

	public static final ComponentType[] ALL_BUTTON_TYPES = new ComponentType[] { BUTTON, PUSH_BUTTON, LW_BUTTON };
	public static final ComponentType[] ALL_TEXTFIELD_TYPES = new ComponentType[] { TEXT_FIELD, TEXT_AREA, SELECT_FIELD };
	public static final ComponentType[] ALL_SCROLL_BAR_TYPES = new ComponentType[] { SCROLL_BAR, SCROLL_BAR_BOX };

	private String className;

	private ComponentType(String className) {
		this.className = className;
	}

	public String toString() {
		return className;
	}

	/**
	 * Check if a given object matches this component type.
	 */
	public boolean matches(Object o) {
		return className.equals(o.getClass().getName());
	}
}
