package org.harper.bookstore.ui.store;

import java.util.Date;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class TransferTableData extends AbstractTableData {

	public TransferTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Number", "number"));
		descBeans.add(new ColumnDescBean(Transfer.Status.class,"Status","status"));
		descBeans.add(new ColumnDescBean(Date.class,"Create Date","createDate"));
		descBeans.add(new ColumnDescBean(StoreSite.class,"From Site","fromSite"));
		descBeans.add(new ColumnDescBean(StoreSite.class,"To Site","toSite"));
	}
	
	public TransferTableData(Object bean) {
		this();
		setBean(bean);
	}
}
