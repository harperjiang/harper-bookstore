package org.harper.bookstore.ui.setting;

import org.harper.frm.gui.swing.manager.SaveBeanInfo;
import org.harper.frm.gui.swing.manager.SaveableBean;

@SaveBeanInfo(name = "ContactInfo")
public class ContactInfoBean extends SaveableBean {

	private String name;

	private String address;

	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
