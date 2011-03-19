package org.harper.bookstore.ui.posmode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WordKeyListener extends KeyAdapter {

	private StringBuilder builder;

	private Callback callback;

	public WordKeyListener(Callback cbk) {
		builder = new StringBuilder();
		this.callback = cbk;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			callback.sayWord(builder.toString());
			builder = new StringBuilder();
		}
		else
			builder.append(e.getKeyChar());
	}

	public static interface Callback {
		public void sayWord(String word);
	}
}
