package org.harper.bookstore.ui.common;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.store.StoreSite;

public class SiteTableRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2251638607613336359L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel superLabel = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		if (value instanceof StoreSite)
			superLabel.setText(((StoreSite) value).getName());
		return superLabel;
	}
}
