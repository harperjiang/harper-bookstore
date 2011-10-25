package org.harper.frm.gui.swing.comp.window;

import javax.swing.JInternalFrame;

public abstract class JPowerWindowEditor extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1623575609160884773L;

	private JPowerWindow managerWindow;

	public JPowerWindow getManagerWindow() {
		return managerWindow;
	}

	public void setManagerWindow(JPowerWindow managerWindow) {
		this.managerWindow = managerWindow;
	}

	public JPowerWindowEditor(String title) {
		super(title, true, true, true, true);
	}
}
