package org.harper.bookstore.ui.store;

import java.util.Date;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class QueryStockTakingTableData extends AbstractTableData {

	public QueryStockTakingTableData() {
		super();
		descBeans.add(new ColumnDescBean(Date.class, "Stock Taking Date",
				"date"));
		descBeans.add(new ColumnDescBean(String.class, "Number", "number"));
		descBeans.add(new ColumnDescBean(String.class,"Status","statusStr"));
	}
	
	public QueryStockTakingTableData(Object bean) {
		this();
		setBean(bean);
	}
}
