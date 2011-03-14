package org.harper.frm.gui.swing.comp.tree.data;

import java.awt.Image;

public class DefaultTreeData implements TreeData {

	private String text;

	private Image icon;

	public DefaultTreeData() {
		this(null, null);
	}

	public DefaultTreeData(String text) {
		this(text, null);
	}

	public DefaultTreeData(String text, Image icon) {
		this.text = text;
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public Image getIcon() {
		return icon;
	}

}
