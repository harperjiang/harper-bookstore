package org.harper.bookstore.ui.order;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class PartialSendBean extends AbstractBean {

	private List<PartialSendItemBean> beans;

	public List<PartialSendItemBean> getBeans() {
		return beans;
	}

	public void setBeans(List<PartialSendItemBean> beans) {
		List<PartialSendItemBean> old = getBeans();
		this.beans = beans;
		firePropertyChange(new PropertyChangeEvent(this, "beans", old, beans));
	}

}
