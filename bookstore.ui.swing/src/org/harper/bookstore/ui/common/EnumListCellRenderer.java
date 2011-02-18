package org.harper.bookstore.ui.common;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class EnumListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2757383410751951305L;

	private Class<? extends Enum> enumClass;

	private String defaultValue;

	private ResourceBundle res;

	public EnumListCellRenderer(Class<? extends Enum> enumClass,
			ResourceBundle res, String defaultValue) {
		this.enumClass = enumClass;
		this.defaultValue = defaultValue;
		this.res = res;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		String text = null;
		if (null == value)
			text = defaultValue;
		else
			text = ((Enum) value).name();
		text = res.getString(text);
		label.setText(text);
		return label;
	}
}
