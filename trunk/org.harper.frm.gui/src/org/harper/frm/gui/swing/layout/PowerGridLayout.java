package org.harper.frm.gui.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowerGridLayout implements LayoutManager {

	private int column = 1;

	private Map<Component, PowerGridData> datas;

	public PowerGridLayout(int col) {
		super();
		this.column = col;
		this.datas = new HashMap<Component, PowerGridData>();
	}

	public void setLayoutData(Component comp, PowerGridData data) {
		datas.put(comp, data);
	}

	public void layoutContainer(Container parent) {

		List<List<Component>> maps = new ArrayList<List<Component>>();
		for (int i = 0; i < column; i++)
			maps.add(new ArrayList<Component>());

		int childrenCount = parent.getComponentCount();
		for (int i = 0; i < childrenCount; i++) {
			Component comp = parent.getComponent(i);
			PowerGridData data = new PowerGridData();
			if (datas.containsKey(comp))
				data = datas.get(comp);

		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

}
