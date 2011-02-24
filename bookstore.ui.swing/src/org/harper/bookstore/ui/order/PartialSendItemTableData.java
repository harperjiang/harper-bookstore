package org.harper.bookstore.ui.order;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class PartialSendItemTableData extends AbstractEditableTableData {

	public PartialSendItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class,"ISBN","book.isbn"));
		descBeans.add(new ColumnDescBean(String.class,"Name","book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE,"Total","count"));
		descBeans.add(new ColumnDescBean(Integer.TYPE,"To Send","send"));
	}
	
	public PartialSendItemTableData(Object bean) {
		this();
		this.setBean(bean);
	}
}
