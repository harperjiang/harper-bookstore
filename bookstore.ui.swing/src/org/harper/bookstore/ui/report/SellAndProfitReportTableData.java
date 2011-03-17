package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.util.Date;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class SellAndProfitReportTableData extends AbstractTableData {

	public SellAndProfitReportTableData() {
		descBeans.add(new ColumnDescBean(Date.class, "Date", "time"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Selling", "selling"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Profit", "profit"));
	}

	public SellAndProfitReportTableData(Object bean) {
		this();
		setBean(bean);
	}
}
