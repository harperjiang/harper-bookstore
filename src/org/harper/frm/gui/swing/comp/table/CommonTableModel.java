/*
 * author Harper Jiang
 * 
 * created in 2006-2-9 9:06:43
 */
package org.harper.frm.gui.swing.comp.table;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.harper.frm.gui.swing.comp.table.data.ColorTableData;
import org.harper.frm.gui.swing.comp.table.data.ETableData;
import org.harper.frm.gui.swing.comp.table.data.EditableTableData;
import org.harper.frm.gui.swing.comp.table.data.TableData;

public class CommonTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5320050214189425884L;

	protected Class<? extends TableData> refClass;

	protected List<TableData> tableData = new Vector<TableData>();

	protected String[] COLUMN_NAMES = new String[] {};

	protected Map<Dimension, Boolean> cellEditableMap;

	protected Vector<Boolean> cellEditable;

	protected boolean autoAdd;

	public CommonTableModel() {
		super();
	}

	public void initialize(Class<? extends TableData> ref) {
		try {
			TableData td = ref.newInstance();
			COLUMN_NAMES = td.getColumnNames();
			refClass = ref;
			tableData = new Vector<TableData>();
			cellEditable = new Vector<Boolean>(COLUMN_NAMES.length);
			for (int i = 0; i < COLUMN_NAMES.length; i++) {
				cellEditable.add(Boolean.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public void initialize(Class<? extends TableData> ref, int rows) {
		try {
			initialize(ref);
			for (int i = 0; i < rows; i++)
				tableData.add(ref.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setData(List<TableData> data) {
		if (data == null)
			data = new Vector<TableData>();
		tableData = data;
		fireTableDataChanged();
	}

	public List getData() {
		return tableData;
	}

	public int getRowCount() {
		if (tableData == null)
			return 0;
		return tableData.size();
	}

	public boolean isAutoAdd() {
		return autoAdd;
	}

	public void setAutoAdd(boolean autoAdd) {
		this.autoAdd = autoAdd;
	}

	public int getColumnCount() {
		if (COLUMN_NAMES == null)
			return 0;
		return COLUMN_NAMES.length;
	}

	public String getColumnName(int columnIndex) {
		if (COLUMN_NAMES == null)
			return null;
		return COLUMN_NAMES[columnIndex];
	}

	public void setCellEditable(int column, boolean editable) {
		cellEditable.set(column, Boolean.valueOf(editable));
	}

	public void setCellEditable(int row, int column, boolean editable) {
		getCellEditableMap().put(new Dimension(row, column), editable);
	}

	protected Map<Dimension, Boolean> getCellEditableMap() {
		if (null == cellEditableMap)
			cellEditableMap = new HashMap<Dimension, Boolean>();
		return cellEditableMap;
	}

	public boolean isCellEditable(int row, int col) {
		if (getCellEditableMap().containsKey(new Dimension(row, col)))
			return getCellEditableMap().get(new Dimension(row, col));
		return ((Boolean) cellEditable.get(col)).booleanValue();
	}

	public void setTableEditable(boolean editable) {
		for (int i = 0; i < getColumnCount(); i++)
			setCellEditable(i, editable);
	}

	public Class getColumnClass(int c) {
		try {
			if (tableData.get(0) == null)
				return ((TableData) refClass.newInstance()).getColumnClass(c);
			return ((TableData) tableData.get(0)).getColumnClass(c);
		} catch (Exception e) {
			e.printStackTrace();
			// return ((TableData)refClass.newInstance()).getColumnClass(c);
			return null;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= getRowCount() || columnIndex >= getColumnCount())
			return null;
		try {
			// TableData t = (TableData) tableData.get(rowIndex);
			if (tableData == null || tableData.get(rowIndex) == null)
				return null;
			return ((TableData) tableData.get(rowIndex)).getColumn(columnIndex);
		} catch (ClassCastException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public void setValueAt(Object object, int rowIndex, int columnIndex) {
		if (object == null)
			return;
		System.out.println(object.getClass().getName());
		if (rowIndex >= getRowCount() || columnIndex >= getColumnCount())
			return;
		try {
			EditableTableData t = (EditableTableData) tableData.get(rowIndex);
			if (t == null)
				t = (EditableTableData) refClass.newInstance();
			((EditableTableData) tableData.get(rowIndex)).setColumn(
					columnIndex, object);
			fireTableCellUpdated(rowIndex, columnIndex);
			if (autoAdd && rowIndex == tableData.size() - 1) {
				tableData.add(tableData.get(0).getClass().newInstance());
				fireTableDataChanged();
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public int getColumnWidth(int col) {
		TableData td;
		try {
			td = refClass.newInstance();
			if (td instanceof ETableData)
				return ((ETableData) td).getColumnWidth(col);
			return 100;
		} catch (Exception e) {
			e.printStackTrace();
			return 100;
		}

	}

	public Color getColor(int row, int col) {
		Object o = getData().get(row);
		if (o instanceof ColorTableData)
			return ((ColorTableData) o).getColor(row, col);
		if (row % 2 == 0)
			return Color.WHITE;
		else
			return new Color(0xF1FFFF);
	}

	public void addRow(TableData data) {
		try {
			if (data != null)
				assert refClass.isInstance(data);
			else
				data = refClass.newInstance();
			int row = getRowCount();
			tableData.add((TableData) data);
			fireTableRowsInserted(row, row);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertRow(int index, TableData data) {
		try {
			if (data != null)
				assert refClass.isInstance(data);
			else
				data = refClass.newInstance();
			tableData.add(index, data);
			fireTableRowsInserted(index, index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRow(int index) {
		try {
			if (index < getRowCount()) {
				tableData.remove(index);
				fireTableRowsDeleted(index, index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateRow(int index, TableData data) {
		try {
			if (data != null && refClass.isInstance(data)
					&& index < getRowCount()) {
				tableData.set(index, data);
				fireTableRowsUpdated(index, index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
