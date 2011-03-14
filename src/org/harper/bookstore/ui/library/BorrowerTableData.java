package org.harper.bookstore.ui.library;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class BorrowerTableData extends AbstractTableData {

	
	public BorrowerTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Name", "name"));
		descBeans.add(new ColumnDescBean(String.class,"Company","company"));
	}
	
	public BorrowerTableData(Object bean) {
		this();
		setBean(bean);
	}
}
