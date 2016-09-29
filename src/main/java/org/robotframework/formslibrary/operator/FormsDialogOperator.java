package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Dialog;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.WindowOperator;
import org.robotframework.formslibrary.context.FormsContext;
import org.robotframework.swing.common.Identifier;
import org.robotframework.swing.operator.ComponentWrapper;

public class FormsDialogOperator extends WindowOperator implements ComponentWrapper {

	public FormsDialogOperator(ComponentChooser chooser) {
		super(waitWindow(chooser));
	}

	public FormsDialogOperator(String title) {
		super(waitWindow(getComponentChooser(title)));

	}

	private static ComponentChooser getComponentChooser(String title) {
		Identifier identifier = new Identifier(title);
		if (identifier.isRegExp()) {
			return createComponentChooser(identifier.asString(), true);
		} else {
			return createComponentChooser(title, false);
		}
	}

	private static ComponentChooser createComponentChooser(final String title, boolean regex) {
		return new ComponentChooser() {

			@Override
			public String getDescription() {
				return "Dialog with name or title '" + title + "'";
			}

			@Override
			public boolean checkComponent(Component comp) {
				if (!(comp instanceof Dialog))
					return false;
				if (regex) {
					String val = comp == null ? "" : ((Dialog) comp).getTitle();
					return val.matches(title);
				} else {
					return eq(title, comp) || org.robotframework.swing.util.ObjectUtils.nullSafeEquals(((Dialog) comp).getTitle(), title);
				}

			}

			private boolean eq(final String title, Component comp) {
				return org.robotframework.swing.util.ObjectUtils.nullSafeEquals(comp.getName(), title);
			}
		};
	}

	public void setAsContext() {
		FormsContext.setContext(this);
	}

}
