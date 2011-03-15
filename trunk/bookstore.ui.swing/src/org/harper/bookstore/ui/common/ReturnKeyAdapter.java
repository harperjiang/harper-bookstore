package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

/**
 * 
 * @author Harper
 * @deprecated Use ReturnKeyListener instead
 */
public class ReturnKeyAdapter extends KeyAdapter {

	private ActionThread run;

	public ReturnKeyAdapter(ActionThread run) {
		this.run = run;
	}

	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {
			if (null != run) {
				Thread thread = new Thread(run);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			((JTextComponent) e.getComponent()).setText(null);
		}
	}
}
