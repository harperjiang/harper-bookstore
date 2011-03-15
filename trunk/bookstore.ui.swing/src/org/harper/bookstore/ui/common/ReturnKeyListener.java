package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class ReturnKeyListener extends KeyAdapter {

	private Runnable run;

	private boolean clear;

	public ReturnKeyListener(Runnable run) {
		this.run = run;
	}

	public ReturnKeyListener(Runnable run, boolean clear) {
		this.run = run;
		this.clear = clear;
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != run)
				run.run();
			if (clear && e.getComponent() instanceof JTextComponent)
				((JTextComponent) e.getComponent()).setText(null);
		}
	}
}
