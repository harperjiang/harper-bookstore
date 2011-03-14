package org.harper.bookstore.ui.common;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import org.harper.bookstore.domain.deliver.ExpressCompany;

public class ExpressCompanyCombo extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782097424836445966L;

	public ExpressCompanyCombo() {
		for (ExpressCompany ec : ExpressCompany.values()) {
			addItem(ec);
		}
		setRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -820159456147708676L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(
						list, value, index, isSelected, cellHasFocus);
				label.setText(((ExpressCompany) value).getDisplayName());
				return label;
			}

		});
	}
}
