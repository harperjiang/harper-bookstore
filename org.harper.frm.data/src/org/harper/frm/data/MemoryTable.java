package org.harper.frm.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.harper.frm.data.util.IPartitionable;
import org.harper.frm.data.util.PartitionableList;



public class MemoryTable implements ITable, Serializable, IPartitionable<ArrayList<Object>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1143626012280210385L;

	private PartitionableList<ArrayList<Object>> datas;

	private Map<String,String> properties;
	
	private MemoryTable() {
		super();
		columnNames = new ArrayList<String>();
		datas = new PartitionableList<ArrayList<Object>>();
		properties = new HashMap<String,String>();
	}

	public Map<String,String> getProperties() {
		return properties;
	}
	
	public MemoryTable(String[] colNames) {
		this();
		initColumnNames(colNames);
	}

	public MemoryTable(int columnCount) {
		this();
		initColumnNames(columnCount);
	}

	private ArrayList<String> columnNames;

	protected void initColumnNames(String[] colNames) {
		columnNames.clear();
		for (int i = 0; i < colNames.length; i++)
			columnNames.add(colNames[i]);
	}

	protected void initColumnNames(int columnCount) {
		columnNames.clear();
		for (int i = 0; i < columnCount; i++) {
			if (i < 26)
				columnNames.add(new String(new char[] { (char) ('A' + i) }));
			else {
				StringBuilder newname = new StringBuilder();
				String last = columnNames.get(i - 1);
				char[] chars = last.toCharArray();
				int count = chars.length - 1;
				for (count = chars.length - 1; count >= 0; count--)
					if (chars[count] < 'Z') {
						chars[count] += 1;
						break;
					} else {
						chars[count] = 'A';
					}
				if (count == -1)
					newname.append('A');
				newname.append(chars);
				columnNames.add(newname.toString());
			}
		}
	}

	private ArrayList<Class<?>> columnClasses = null;

	protected void initDatas(Object[][] datas) {
		Validate.isTrue(datas.length > 0);
		Validate.isTrue(datas[0].length > 0);
		initColumnNames(datas[0].length);
		for (int i = 0; i < datas.length; i++) {
			addRow(datas[i]);
		}
	}

	// private void initColumnClasses(List<Object> objects) {
	// getColumnClasses().clear();
	// for (Object obj : objects)
	// getColumnClasses().add(obj != null ? obj.getClass() : Object.class);
	// }

	// private void initColumnClasses(Object[] objects) {
	// getColumnClasses().clear();
	// for (int i = 0; i < objects.length; i++)
	// getColumnClasses().add(
	// objects[i] != null ? objects[i].getClass() : Object.class);
	// }

	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	public Class<?> getColumnClass(int columnIndex) {
		// TODO Maintain column class later.
		return Object.class;
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return datas.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return datas.get(rowIndex).get(columnIndex);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		datas.get(rowIndex).set(columnIndex, value);
	}

	protected ArrayList<ArrayList<Object>> getDatas() {
		return datas;
	}

	protected ArrayList<Class<?>> getColumnClasses() {
		if (columnClasses == null)
			columnClasses = new ArrayList<Class<?>>();
		return columnClasses;
	}

	public void addRow(Object[] data) {
		ArrayList<Object> row = new ArrayList<Object>();
		for (int i = 0; i < data.length; i++)
			row.add(data[i]);
		getDatas().add(row);
	}

	public void addRow(List<Object> data) {
		ArrayList<Object> row = new ArrayList<Object>();
		row.addAll(data);
		getDatas().add(row);
	}

	public void addRows(List<List<Object>> datas) {
		for (List<Object> data : datas)
			addRow(data);
	}

	public void removeRow(int row) {
		getDatas().remove(row);
	}

	public List<PartitionableList<ArrayList<Object>>> partition(int partition) {
		return datas.partition(partition);
	}

	public List<ArrayList<Object>> getElements() {
		return datas.getElements();
	}

	public int size() {
		return datas.size();
	}


}
