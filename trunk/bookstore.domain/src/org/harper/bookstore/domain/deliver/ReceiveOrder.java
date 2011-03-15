package org.harper.bookstore.domain.deliver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.store.StoreSite;

public class ReceiveOrder extends Entity {
	public static enum ReceiveType {
		RETURN/* 退货 */, RECEIVE;
		/* 到货 */

		public String desc() {
			ResourceBundle rb = ResourceBundle
					.getBundle("/org/harper/bookstore/domain/deliver/ReceiveType");
			return rb.getString(name());
		}
	}

	private ExpressCompany company;

	private String number;

	private int status;

	private ContactInfo sender;

	private StoreSite site;

	private Date createDate;

	private Date receiveDate;

	private List<ReceiveItem> items;

	private String type;

	private String refNumber;

	private String remark;

	public ReceiveOrder() {
		super();
		sender = new ContactInfo();
		createDate = new Date();
		items = new ArrayList<ReceiveItem>();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ExpressCompany getCompany() {
		return company;
	}

	public void setCompany(ExpressCompany company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public ContactInfo getSender() {
		return sender;
	}

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public List<ReceiveItem> getItems() {
		return items;
	}

	public void addItems(ReceiveItem item) {
		getItems().add(item);
		item.setHeader(this);
	}

	public void removeItems(ReceiveItem item) {
		getItems().remove(item);
		item.setHeader(null);
	}

	public String getType() {
		return ReceiveType.valueOf(type).desc();
	}

	public void setType(ReceiveType type) {
		this.type = type.name();
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
