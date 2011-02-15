package org.harper.bookstore.ui.store;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class StoreSiteTableData extends AbstractEditableTableData {

	public StoreSiteTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class, "Name", "name", 200));
		descBeans.add(new ColumnDescBean(String.class, "Description", "desc",
				200));
		descBeans.add(new ColumnDescBean(Boolean.class, "Valid", "valid", 50));
		descBeans.add(new ColumnDescBean(Boolean.class, "For Output",
				"forOutput", 50));
	}

	public StoreSiteTableData(Object bean) {
		this();
		setBean(bean);
	}
}
