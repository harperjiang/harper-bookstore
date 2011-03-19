package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class ReturnKeyListener extends KeyAdapter {

	public static interface Callback {
		public void call(String text);
	}

	private Callback callback;

	private boolean clear;

	public ReturnKeyListener(Callback call) {
		this(call, true);
	}

	public ReturnKeyListener(Callback call, boolean clear) {
		this.callback = call;
		this.clear = clear;
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != callback && e.getComponent() instanceof JTextComponent) {
				callback.call(((JTextComponent) e.getComponent()).getText());
			}
			if (clear && e.getComponent() instanceof JTextComponent)
				((JTextComponent) e.getComponent()).setText(null);
		}
	}
}
