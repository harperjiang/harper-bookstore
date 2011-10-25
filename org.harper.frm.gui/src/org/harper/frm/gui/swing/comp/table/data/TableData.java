/*
 * author Harper Jiang
 * 
 * created in 2006-2-9 9:16:27
 */
package org.harper.frm.gui.swing.comp.table.data;

import java.util.List;

import org.harper.frm.gui.swing.comp.table.ColumnDescBean;

public interface TableData {
	public String[] getColumnNames();
    public Object getColumn(int index);
    public Class getColumnClass(int index);
    public List<ColumnDescBean> getDescriptors(); 
}
