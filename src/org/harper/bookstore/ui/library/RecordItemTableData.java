package org.harper.bookstore.ui.library;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class RecordItemTableData extends AbstractEditableTableData {

	public RecordItemTableData() {
		super();
		descBeans.add(new ColumnDescBean(String.class,"ISBN","book.isbn"));
		descBeans.add(new ColumnDescBean(String.class,"Name","book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE,"Count","count"));
	}
	
	public RecordItemTableData(Object bean){
		this();
		setBean(bean);
	}
}
