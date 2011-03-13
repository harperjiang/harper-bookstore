package org.harper.bookstore.ui.common;

import javax.swing.JComboBox;

public class SiteComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963257440581798326L;

	public SiteComboBox() {
		super();
		
		setRenderer(new SiteListRenderer());
	}
}
