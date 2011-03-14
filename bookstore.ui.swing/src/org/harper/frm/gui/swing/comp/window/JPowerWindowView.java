package org.harper.frm.gui.swing.comp.window;

import javax.swing.JPanel;

public abstract class JPowerWindowView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4040186520353947871L;
	
	private String viewName;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
