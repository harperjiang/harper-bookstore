package org.harper.bookstore.ui.store;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class StockAlertTableData extends AbstractEditableTableData {

	public StockAlertTableData() {
		super();

		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans
				.add(new ColumnDescBean(String.class, "Book Name", "book.name"));
		descBeans.add(new ColumnDescBean(String.class, "Store Site",
				"site.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Warn Threshold",
				"warnThreshold"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Error Threshold",
				"errorThreshold"));

	}

	public StockAlertTableData(Object bean) {
		this();
		setBean(bean);
	}
}
