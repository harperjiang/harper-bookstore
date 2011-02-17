package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class ReturnKeyAdapter extends KeyAdapter {

	private ExceptionRunnable run;

	public ReturnKeyAdapter(ExceptionRunnable run) {
		this.run = run;
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != run)
				try {
					run.run();
				} catch (Exception ex) {
					run.handleException(ex);
				} finally {

					((JTextComponent) e.getComponent()).setText(null);
				}
		}
	}
}
