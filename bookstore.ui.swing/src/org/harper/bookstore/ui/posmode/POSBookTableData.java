package org.harper.bookstore.ui.posmode;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class POSBookTableData extends AbstractTableData {

	public POSBookTableData() {
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans
				.add(new ColumnDescBean(String.class, "图书名称", "book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "计数", "count"));
	}
	
	public POSBookTableData(Object bean) {
		this();
		setBean(bean);
	}
}
