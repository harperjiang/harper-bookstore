package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.util.Date;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class SAPReportTableData extends AbstractTableData {

	public SAPReportTableData() {
		descBeans.add(new ColumnDescBean(Date.class, "Date", "time"));
		descBeans
				.add(new ColumnDescBean(BigDecimal.class, "Selling", "selling"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Profit", "profit"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Profit %",
				"profitRate"));
	}

	public SAPReportTableData(Object bean) {
		this();
		setBean(bean);
	}
}
