package org.harper.bookstore.ui.common;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.harper.bookstore.domain.store.StoreSite;

public class SiteListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = -8062361344485367288L;

	@Override
	public Component getListCellRendererComponent(JList list,
			Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel superLabel = (JLabel) super
				.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
		if (value instanceof StoreSite)
			superLabel.setText(((StoreSite) value).getName());
		return superLabel;
	}
}
