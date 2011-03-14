package org.harper.bookstore.ui.report;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class PurchaseReportTableData extends AbstractTableData {

	public PurchaseReportTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class,"ISBN","isbn"));
		descBeans.add(new ColumnDescBean(String.class,"Book Name","bookName"));
		descBeans.add(new ColumnDescBean(Integer.TYPE,"Count","count"));
		descBeans.add(new ColumnDescBean(BigDecimal.class,"Revenue","revenue"));
	}
	
	public PurchaseReportTableData(Object bean) {
		this();
		this.setBean(bean);
	}
}
