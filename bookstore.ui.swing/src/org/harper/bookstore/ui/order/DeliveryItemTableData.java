package org.harper.bookstore.ui.order;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class DeliveryItemTableData extends AbstractEditableTableData {

	public DeliveryItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN",
				"orderItem.book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name",
				"orderItem.book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Total",
				"orderItem.count"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
	}

	public DeliveryItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
