package org.harper.bookstore.ui.delivery;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class ReceiveItemTableData extends AbstractEditableTableData {

	public ReceiveItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN",
				"book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name",
				"book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
	}

	public ReceiveItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
