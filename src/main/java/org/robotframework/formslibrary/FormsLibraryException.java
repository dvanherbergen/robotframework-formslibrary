package org.robotframework.formslibrary;

public class FormsLibraryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final boolean ROBOT_SUPPRESS_NAME = true;

	public FormsLibraryException(String message) {
		super(message);
	}
	
	public FormsLibraryException(Throwable t) {
		super(t);
	}

	public FormsLibraryException(String message, Throwable t) {
		super(message, t);
	}
}
