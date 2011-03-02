package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class ReturnKeyAdapter extends KeyAdapter {

	private ActionThread run;

	public ReturnKeyAdapter(ActionThread run) {
		this.run = run;
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != run)
				run.start();
			((JTextComponent) e.getComponent()).setText(null);
		}
	}
}
