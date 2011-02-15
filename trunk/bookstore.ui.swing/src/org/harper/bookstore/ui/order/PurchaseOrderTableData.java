package org.harper.bookstore.ui.order;

import java.util.Date;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;
import org.harper.frm.gui.swing.comp.table.data.ETableData;

public class PurchaseOrderTableData extends AbstractTableData implements ETableData {

	public PurchaseOrderTableData() {
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.order_num"), "number", //$NON-NLS-1$ //$NON-NLS-2$
				80));
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.status"), "orderStatus", //$NON-NLS-1$ //$NON-NLS-2$
				80));
		descBeans.add(new ColumnDescBean(Date.class, Messages.getString("PurchaseOrderTableData.create_date"), //$NON-NLS-1$
				"createDate", 60)); //$NON-NLS-1$
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.customer"), //$NON-NLS-1$
				"party.id", 80)); //$NON-NLS-1$
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.express_status"), //$NON-NLS-1$
				"orderExpressStatus", 80)); //$NON-NLS-1$
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.consignee"), //$NON-NLS-1$
				"contact.name", 50)); //$NON-NLS-1$
		descBeans.add(new ColumnDescBean(String.class, Messages.getString("PurchaseOrderTableData.address"), //$NON-NLS-1$
				"contact.address", 400)); //$NON-NLS-1$
	}

	public PurchaseOrderTableData(Object bean) {
		this();
		setBean(bean);
	}

	@Override
	public int getColumnWidth(int columnIndex) {
		return getDescriptors().get(columnIndex).getWidth();
	}
}
