package org.harper.bookstore.ui.store;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class NewStoreSiteBean extends AbstractBean {

	private String name;

	private String desc;

	private boolean forOutput;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getForOutput() {
		return forOutput;
	}

	public void setForOutput(Boolean forOutput) {
		this.forOutput = forOutput;
	}

}
