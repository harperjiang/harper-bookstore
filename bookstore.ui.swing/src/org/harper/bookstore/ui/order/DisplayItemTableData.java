package org.harper.bookstore.ui.order;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class DisplayItemTableData extends AbstractTableData {

	public DisplayItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Name", "name", 500));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count", 80));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Unit Price",
				"unitPrice", 80));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Total Price",
				"actualPrice", 80));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Discount",
				"discount", 80));
	}

	public DisplayItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
