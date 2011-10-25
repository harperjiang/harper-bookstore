/*
 * author Harper Jiang
 * 
 * created in 2006-2-24 13:29:05
 */
package org.harper.frm.gui.swing.comp.table.data;

import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;

public class AbstractTableData implements TableData {

	protected int maxIndex = 0;

	protected Vector<ColumnDescBean> descBeans = new Vector<ColumnDescBean>();

	private Object bean;

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getColumn(int index) {
		if (descBeans.size() <= index)
			return null;
		ColumnDescBean tdb = (ColumnDescBean) descBeans.get(index);
		try {
			return PropertyUtils.getProperty(getBean(), tdb.getAccessStr());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Class getColumnClass(int index) {
		if (descBeans.size() <= index)
			return null;
		ColumnDescBean tdb = (ColumnDescBean) descBeans.get(index);
		try {
			return tdb.getClazz();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] getColumnNames() {
		String[] names = new String[descBeans.size()];
		for (int i = 0; i < descBeans.size(); i++)
			names[i] = ((ColumnDescBean) descBeans.get(i)).getName();
		return names;
	}

	public List<ColumnDescBean> getDescriptors() {
		return descBeans;
	}

}
