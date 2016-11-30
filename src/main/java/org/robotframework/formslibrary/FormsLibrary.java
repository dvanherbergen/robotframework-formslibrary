package org.robotframework.formslibrary;

import org.robotframework.swing.SwingLibrary;

/**
 * Extend the default swinglibrary with keywords needed to work with oracle
 * forms.
 */
public class FormsLibrary extends SwingLibrary {

	// TODO stop extending SwingLibrary, only include classes that are really
	// needed?

	public FormsLibrary() {
		super("org/robotframework/formslibrary/keyword/*.class");
	}

}
