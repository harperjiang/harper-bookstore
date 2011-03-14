package org.harper.bookstore.ui.print;

import org.harper.bookstore.domain.deliver.ExpressCompany;
import org.harper.frm.gui.swing.manager.SaveBeanInfo;
import org.harper.frm.gui.swing.manager.SaveableBean;

@SaveBeanInfo(name = "print_express")
public class PrintExpressOrderBean extends SaveableBean {

	private ExpressCompany company = ExpressCompany.values()[0];

	private String fromName;

	private String fromAddress;

	private String fromMobile;

	private String toName;

	private String toAddress;

	private String toMobile;

	public PrintExpressOrderBean() {
		super();
		load();
	}

	public ExpressCompany getCompany() {
		return company;
	}

	public void setCompany(ExpressCompany company) {
		this.company = company;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(String fromMobile) {
		this.fromMobile = fromMobile;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}
}
