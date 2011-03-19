package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class ReturnKeyListener extends KeyAdapter {

	public static interface Callback {
		public void call(String text);
	}

	private Callback callback;

	private StringBuilder buffer;

	private boolean clear;

	public ReturnKeyListener(Callback call) {
		this(call, true);
	}

	public ReturnKeyListener(Callback call, boolean clear) {
		this.callback = call;
		this.clear = clear;
		this.buffer = new StringBuilder();
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != callback) {
				callback.call(buffer.toString());
				buffer = new StringBuilder();
			}
			if (clear && e.getComponent() instanceof JTextComponent)
				((JTextComponent) e.getComponent()).setText(null);
		} else {
			buffer.append(e.getKeyChar());
		}
	}
}
