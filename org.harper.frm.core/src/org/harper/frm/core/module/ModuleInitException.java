package org.harper.frm.core.module;

public class ModuleInitException extends RuntimeException {

	private static final long serialVersionUID = 8379498641666505431L;

	public ModuleInitException() {
	}

	public ModuleInitException(String message) {
		super(message);
	}

	public ModuleInitException(Throwable cause) {
		super(cause);
	}

	public ModuleInitException(String message, Throwable cause) {
		super(message, cause);
	}

}
