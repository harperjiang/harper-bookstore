package org.harper.bookstore.ui.common;

import javax.swing.JComboBox;

import org.harper.bookstore.domain.profile.Source;

public class SourceComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2935654322300962929L;

	public SourceComboBox() {
		super();
		for(Source src :Source.values())
			addItem(src.name());
	}
}
