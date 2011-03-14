package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class BookInfoTableData extends AbstractEditableTableData {

	public BookInfoTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name", "name"));
		descBeans.add(new ColumnDescBean(String.class, "Description", "desc"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "List Price",
				"price"));
	}

	public BookInfoTableData(Object book) {
		this();
		setBean(book);
	}
}
