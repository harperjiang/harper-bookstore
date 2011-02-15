package org.harper.frm.data.formatter;

import java.text.MessageFormat;

public class FormatterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3869563080327812579L;

	private String code;

	public FormatterException(long code) {
		super();
		this.code = PREFIX + code;
	}

	public FormatterException(String msg, long code) {
		super(msg);
		this.code = PREFIX + code;
	}

	public String getCode() {
		return code;
	}

	/*
	 * Exception code
	 */
	static final String PREFIX = "FORMAT-";

	static final long EXCEED_ROW_LIMIT = 1;

	public static FormatterException exceedRowLimit() {
		return new FormatterException(MessageFormat.format(
				"{0}{1}:exceeds row limit", PREFIX, EXCEED_ROW_LIMIT),
				EXCEED_ROW_LIMIT);
	}
}
