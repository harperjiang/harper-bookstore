package org.harper.bookstore.ui.common;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class NegAmtRedTableRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8735270857583475402L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel superLabel = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		if ((value instanceof BigDecimal && ((BigDecimal) value)
				.compareTo(BigDecimal.ZERO) < 0)
				|| (value instanceof Number && ((Number) value).doubleValue() < 0)) {
			superLabel.setForeground(Color.RED);
		} else
			superLabel.setForeground(Color.BLUE);
		return superLabel;
	}
}
