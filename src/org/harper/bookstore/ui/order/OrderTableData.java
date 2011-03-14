package org.harper.bookstore.ui.order;

import java.util.Date;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;
import org.harper.frm.gui.swing.comp.table.data.ETableData;

public class OrderTableData extends AbstractTableData implements ETableData {

	public OrderTableData() {
		descBeans.add(new ColumnDescBean(String.class, "Order No.", "number",
				80));
		descBeans.add(new ColumnDescBean(String.class, "Status", "orderStatus",
				40));
		descBeans.add(new ColumnDescBean(Date.class, "Create Date",
				"createDate", 60));
		descBeans.add(new ColumnDescBean(String.class, "Customer/Supplier",
				"party.id", 80));
		descBeans.add(new ColumnDescBean(String.class, "Delivery Status",
				"orderExpressStatus", 80));
		descBeans.add(new ColumnDescBean(String.class, "Delivery Number",
				"deliveryNumber", 100));
		descBeans.add(new ColumnDescBean(String.class, "Consignee",
				"contact.name", 50));
		descBeans.add(new ColumnDescBean(String.class, "Address",
				"contact.address", 400));
	}

	public OrderTableData(Object bean) {
		this();
		setBean(bean);
	}

	@Override
	public int getColumnWidth(int columnIndex) {
		return getDescriptors().get(columnIndex).getWidth();
	}
}
