package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

/**
 * 
 * @author Harper
 */
public class CtrlKeyAdapter extends KeyAdapter {

	private int keyChar;

	private Runnable run;

	public CtrlKeyAdapter(int p, Runnable run) {
		this.run = run;
		keyChar = p;
	}

	public void keyReleased(KeyEvent e) {
		if (((KeyEvent.CTRL_DOWN_MASK & e.getModifiersEx()) > 0)
				&& e.getKeyCode() == keyChar) {
			if (null != run)
				run.run();
		}
	}
}
