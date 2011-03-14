package org.harper.frm.gui.swing.comp.table;

public class ColumnDescBean {

	private Class clazz;

	private String name;

	private String accessStr;

	private int width = 0;

	public ColumnDescBean(Class a, String nm, String accStr) {
		super();
		clazz = a;
		name = nm;
		accessStr = accStr;
	}

	public ColumnDescBean(Class a, String b, String d, int e) {
		super();
		clazz = a;
		name = b;
		accessStr = d;
		width = e;
	}

	public String getAccessStr() {
		return accessStr;
	}

	public Class getClazz() {
		return clazz;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
