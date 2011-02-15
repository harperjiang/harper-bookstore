package org.harper.bookstore.ui.store;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class BookReportItemTableData extends AbstractTableData {

	public BookReportItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Site", "siteName"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Remains", "count"));
		descBeans
				.add(new ColumnDescBean(Integer.TYPE, "Available", "available"));
	}

	public BookReportItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
