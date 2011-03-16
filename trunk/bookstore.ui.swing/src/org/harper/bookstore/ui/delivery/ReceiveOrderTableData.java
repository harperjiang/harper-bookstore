package org.harper.bookstore.ui.delivery;

import java.util.Date;

import org.harper.bookstore.domain.deliver.ExpressCompany;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class ReceiveOrderTableData extends AbstractTableData {

	public ReceiveOrderTableData() {
		super();
		descBeans.add(new ColumnDescBean(Date.class, "Create Date",
				"createDate"));
		descBeans.add(new ColumnDescBean(String.class, "Status", "statusStr"));
		descBeans.add(new ColumnDescBean(ExpressCompany.class, "Company",
				"company"));
		descBeans.add(new ColumnDescBean(String.class, "Number", "number"));

		descBeans.add(new ColumnDescBean(String.class, "Consignee",
				"contact.name"));
		descBeans.add(new ColumnDescBean(String.class, "Address",
				"contact.address"));
	}

	public ReceiveOrderTableData(Object bean) {
		this();
		setBean(bean);
	}
}
