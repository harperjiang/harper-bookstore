package org.harper.bookstore.ui.report;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class ProfitRateTableData extends AbstractTableData {

	public ProfitRateTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Range", "rangeDesc"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Percentage",
				"percentage"));
	}

	public ProfitRateTableData(Object bean) {
		this();
		setBean(bean);
	}
}
