package org.harper.frm.core.module;

public class ModuleInitError extends Error {

	private static final long serialVersionUID = -4775091407714840325L;

	public ModuleInitError() {
	}

	public ModuleInitError(String message) {
		super(message);
	}

	public ModuleInitError(Throwable cause) {
		super(cause);
	}

	public ModuleInitError(String message, Throwable cause) {
		super(message, cause);
	}

}
