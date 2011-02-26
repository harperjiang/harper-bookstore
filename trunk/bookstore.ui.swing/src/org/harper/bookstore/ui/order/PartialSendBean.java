package org.harper.bookstore.ui.order;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class PartialSendBean extends AbstractBean {

	private Date sendDate;

	private List<PartialSendItemBean> beans;

	public PartialSendBean() {
		super();
		sendDate = new Date();
		beans = new ArrayList<PartialSendItemBean>();
	}

	public List<PartialSendItemBean> getBeans() {
		return beans;
	}

	public void setBeans(List<PartialSendItemBean> beans) {
		List<PartialSendItemBean> old = getBeans();
		this.beans = beans;
		firePropertyChange(new PropertyChangeEvent(this, "beans", old, beans));
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		Date old = getSendDate();
		this.sendDate = sendDate;
		firePropertyChange(new PropertyChangeEvent(this, "sendDate", old,
				sendDate));
	}

}
