package org.harper.bookstore.ui.common;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8735270857583475402L;

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel superLabel = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		if (value instanceof Date)
			superLabel.setText(format.format(((Date) value)));
		return superLabel;
	}
}
