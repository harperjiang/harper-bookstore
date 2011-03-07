package org.harper.bookstore.ui.delivery;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class ViewDeliveryItemTableData extends AbstractEditableTableData {

	public ViewDeliveryItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "ISBN",
				"orderItem.book.isbn"));
		descBeans.add(new ColumnDescBean(String.class, "Name",
				"orderItem.book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Unit Price",
				"unitCost"));
	}

	public ViewDeliveryItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
