package org.harper.bookstore.ui.common;


public abstract class ActionThread implements Runnable {
	public void run() {
		try {
			execute();
			success();
		} catch (final Exception ex) {
			exception(ex);
		}
	}

	public abstract void execute();

	public void success() {

	}

	public abstract void exception(Exception ex);
}
