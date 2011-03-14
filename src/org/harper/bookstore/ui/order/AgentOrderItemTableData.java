package org.harper.bookstore.ui.order;

import java.math.BigDecimal;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;

public class AgentOrderItemTableData extends AbstractEditableTableData {

	public AgentOrderItemTableData() {
		descBeans.add(new ColumnDescBean(String.class, "ISBN", "book.isbn"));
		descBeans
				.add(new ColumnDescBean(String.class, "Book Name", "book.name"));
		descBeans.add(new ColumnDescBean(Integer.TYPE, "Count", "count"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Unit Price",
				"unitPrice"));
		descBeans.add(new ColumnDescBean(Boolean.class, "Agent Item", "agent"));
		descBeans.add(new ColumnDescBean(BigDecimal.class, "Unit Cost",
				"unitCost"));
	}

	public AgentOrderItemTableData(Object bean) {
		this();
		setBean(bean);
	}
}
