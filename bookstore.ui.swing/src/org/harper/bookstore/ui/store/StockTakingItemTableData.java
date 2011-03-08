package org.harper.bookstore.ui.store;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class StockTakingItemTableData extends AbstractEditableTableData {

	public StockTakingItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans
				.add(new ColumnDescBean(String.class, "Book Name", "book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Expected Count",
				"originCount"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Current Count",
				"currentCount"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Unit Cost",
				"unitPrice"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Discrepancy",
				"discrepancy"));
	}

	public StockTakingItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
