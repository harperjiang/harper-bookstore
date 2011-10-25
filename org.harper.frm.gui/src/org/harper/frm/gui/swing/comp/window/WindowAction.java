package org.harper.frm.gui.swing.comp.window;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class WindowAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5825018566654434068L;
	private Class<?> frameClass;

	public WindowAction(String name, Class<?> frmClass) {
		super(name);
		this.frameClass = frmClass;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			((Window) this.frameClass.newInstance()).setVisible(true);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		} finally {

		}
	}

}
