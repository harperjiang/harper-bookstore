/*
 * author Harper Jiang
 * 
 * created in 2006-2-24 13:30:35
 */
package org.harper.frm.gui.swing.comp.table.data;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;

public class AbstractEditableTableData extends AbstractTableData implements
		EditableTableData {

	public void setColumn(int index, Object o) {
		if (descBeans.size() <= index)
			return;
		ColumnDescBean tdb = (ColumnDescBean) descBeans.get(index);
		try {
			BeanUtilsBean.getInstance().setProperty(getBean(), tdb.getAccessStr(), o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
