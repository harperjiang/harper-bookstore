package org.harper.bookstore.ui.common;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckBoxTableRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2251638607613336359L;

	private JCheckBox checkbox;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(null == checkbox)
			checkbox = new JCheckBox();
		if (value instanceof Boolean)
			checkbox.setSelected((Boolean)value);
		return checkbox;
	}
}
