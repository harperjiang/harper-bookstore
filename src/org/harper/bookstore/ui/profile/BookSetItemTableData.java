package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class BookSetItemTableData extends AbstractEditableTableData {

	public BookSetItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name", "book.name"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Percentage",
				"percentage"));
	}

	public BookSetItemTableData(Object book) {
		this();
		setBean(book);
	}
}
