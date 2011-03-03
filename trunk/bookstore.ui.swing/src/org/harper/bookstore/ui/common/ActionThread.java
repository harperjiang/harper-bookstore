package org.harper.bookstore.ui.common;

import javax.swing.SwingUtilities;

public abstract class ActionThread implements Runnable {
	public void run() {
		try {
			execute();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					success();
				}
			});
		} catch (final Exception ex) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					exception(ex);
				}
			});
		}
	}

	public abstract void execute();

	public void success() {

	}

	public abstract void exception(Exception ex);
}
