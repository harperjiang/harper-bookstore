package org.harper.frm.gui.swing.comp.window;

import javax.swing.JPanel;

public class PreferencePage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6591375364139333704L;
	
	private String pageName;
	
	private String parentPage;

	public PreferencePage(String name) {
		super();
		this.pageName = name;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getParentPage() {
		return parentPage;
	}

	public void setParentPage(String parentPage) {
		this.parentPage = parentPage;
	}


}
