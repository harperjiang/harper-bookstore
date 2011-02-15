package org.harper.frm.core.config;

/**
 * Exception indicating that an requested key is not found in config source.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public class KeyNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1765624326750143406L;

	public KeyNotFoundException(String msg) {
		super(msg);
	}
}
