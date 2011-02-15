package org.harper.frm.data.parser;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428715885110086411L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}
}
