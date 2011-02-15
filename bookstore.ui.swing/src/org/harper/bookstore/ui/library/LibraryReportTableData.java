package org.harper.bookstore.ui.library;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class LibraryReportTableData extends AbstractTableData {

	public LibraryReportTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class,"Book Name","book.name"));
		descBeans.add(new ColumnDescBean(String.class,"ISBN","book.isbn"));
		descBeans.add(new ColumnDescBean(String.class,"Borrower","borrowerDesc"));	
		descBeans.add(new ColumnDescBean(Integer.TYPE,"Outstanding","outstanding"));
	}
	
	public LibraryReportTableData(Object bean) {
		this();
		setBean(bean);
	}
}
