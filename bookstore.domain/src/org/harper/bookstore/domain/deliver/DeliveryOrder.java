package org.harper.bookstore.domain.deliver;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import oracle.toplink.indirection.ValueHolder;
import oracle.toplink.indirection.ValueHolderInterface;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
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

	private ValueHolderInterface items;

	private ContactInfo contact;

	public DeliveryOrder() {
		setStatus(Status.NEW.ordinal());
		contact = new ContactInfo();
		createDate = new Date();
		items = new ValueHolder(new Vector());
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
			StoreSite site = item.getOrderItem().getOrder().getSite();
			site.lock(item.getOrderItem().getBook(), item.getCount());
			BookUnit bu = site.retrieve(item.getOrderItem().getBook(),
					item.getCount());
			Validate.isTrue(bu.getCount() == item.getCount());
			item.setUnitCost(bu.getUnitPrice());
		}
	}

	public void fallback() {
		Validate.isTrue(getStatus() == Status.DELIVERED.ordinal());
		setStatus(Status.RETURNED.ordinal());

		// Modify Storage
		for (DeliveryItem item : getItems()) {
			StoreSite site = item.getOrderItem().getOrder().getSite();
			site.putInto(item.getOrderItem().getBook(), item.getCount(),
					item.getUnitCost());
		}
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DeliveryItem> getItems() {
		return (List<DeliveryItem>) items.getValue();
	}

	public void addItem(DeliveryItem add) {
		add.setHeader(this);
		this.getItems().add(add);
	}

	public void removeItem(DeliveryItem remove) {
		remove.setHeader(null);
		this.getItems().remove(remove);
	}

	// public void setItems(List<DeliveryItem> items) {
	// this.items.setValue(items);
	// for (DeliveryItem item : items)
	// item.setHeader(this);
	// }

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
}
