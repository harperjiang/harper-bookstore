package org.harper.bookstore.ui.delivery;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class DeliveryItemTableData extends AbstractEditableTableData {

	public DeliveryItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN",
				"orderItem.book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name",
				"orderItem.book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Remains",
				"orderItem.unsentCount"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "To Send", "count"));
	}

	public DeliveryItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
