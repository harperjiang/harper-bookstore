package org.harper.bookstore.ui.store;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class TransferItemTableData extends AbstractEditableTableData {

	public TransferItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans
				.add(new ColumnDescBean(String.class, "Book Name", "book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
	}

	public TransferItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
