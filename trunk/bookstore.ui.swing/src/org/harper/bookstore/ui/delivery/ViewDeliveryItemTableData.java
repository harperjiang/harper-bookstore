package org.harper.bookstore.ui.delivery;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class ViewDeliveryItemTableData extends AbstractEditableTableData {

	public ViewDeliveryItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN",
				"orderItem.book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name",
				"orderItem.book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "To Send", "count"));
	}

	public ViewDeliveryItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
