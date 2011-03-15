package org.harper.bookstore.domain.deliver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.store.StoreSite;

public class DeliveryOrder extends Entity {

	private ExpressCompany company = ExpressCompany.values()[0];

	private int status;

	public static enum Status {
		NEW, DRAFT, DELIVERED, RETURNED, EXCEPTION;
	}

	private String number;

	private Date createDate;

	private List<DeliveryItem> items;

	private ContactInfo contact;

	private boolean sendMissed;

	private String remark;

	public DeliveryOrder() {
		setStatus(Status.NEW.ordinal());
		contact = new ContactInfo();
		createDate = new Date();
		items = new ArrayList<DeliveryItem>();
		setValid(true);
	}

	public void create() {
		Validate.isTrue(getStatus() == Status.NEW.ordinal());
		setStatus(Status.DRAFT.ordinal());
	}

	public void deliver() {
		Validate.isTrue(getStatus() == Status.DRAFT.ordinal());
		setStatus(Status.DELIVERED.ordinal());

		// Modify Storage
		for (DeliveryItem item : getItems()) {
			if (item.getOrderItem().isAgent()) {
				item.setUnitCost(item.getOrderItem().getUnitCost());
			} else {
				StoreSite site = item.getOrderItem().getOrder().getSite();
				site.lock(item.getOrderItem().getBook(), item.getCount());
				BookUnit bu = site.retrieve(item.getOrderItem().getBook(),
						item.getCount());
				Validate.isTrue(bu.getCount() == item.getCount());
				item.setUnitCost(bu.getUnitPrice());
			}
			item.getOrderItem().send(item.getCount());
			((PurchaseOrder) item.getOrderItem().getOrder()).makeDelivery();
		}
	}

	public void fallback() {
		Validate.isTrue(getStatus() == Status.DELIVERED.ordinal(),
				"Can only fallback delivered order");
		setStatus(Status.RETURNED.ordinal());

		// Modify Storage
		for (DeliveryItem item : getItems()) {
			StoreSite site = item.getOrderItem().getOrder().getSite();
			site.putInto(item.getOrderItem().getBook(), item.getCount(),
					item.getUnitCost());
			item.getOrderItem().setSentCount(
					item.getOrderItem().getSentCount() - item.getCount());
			((PurchaseOrder) item.getOrderItem().getOrder()).makeDelivery();
		}
	}
	
	public void fallback(List<BookUnit> items) {
		
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
		this.number = null == number ? null : number.toUpperCase();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DeliveryItem> getItems() {
		return items;
	}

	public void addItem(DeliveryItem add) {
		add.setHeader(this);
		this.getItems().add(add);
	}

	public void removeItem(DeliveryItem remove) {
		remove.setHeader(null);
		this.getItems().remove(remove);
	}

	public void removeAllItems() {
		for (DeliveryItem item : getItems()) {
			item.setHeader(null);
		}
		this.getItems().clear();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ContactInfo getContact() {
		return contact;
	}

	public String getStatusStr() {
		return ResourceBundle.getBundle(
				"org.harper.bookstore.domain.deliver.DOStatus").getString(
				Status.values()[getStatus()].name());
	}

	public boolean isSendMissed() {
		return sendMissed;
	}

	public void setSendMissed(boolean sendMissed) {
		this.sendMissed = sendMissed;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
