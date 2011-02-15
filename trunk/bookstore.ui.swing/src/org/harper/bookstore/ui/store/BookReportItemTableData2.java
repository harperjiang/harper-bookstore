package org.harper.bookstore.ui.store;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class BookReportItemTableData2 extends AbstractTableData {

	public BookReportItemTableData2() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name", "book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Remains", "count"));
		descBeans.add(new ColumnDescBean(BigDecimal.class,"Cost","cost"));
	}

	public BookReportItemTableData2(Object bean) {
		this();
		setBean(bean);
	}
}
