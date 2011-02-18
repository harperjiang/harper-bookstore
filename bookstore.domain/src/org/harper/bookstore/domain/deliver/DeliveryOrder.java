package org.harper.bookstore.domain.deliver;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import oracle.toplink.indirection.ValueHolder;
import oracle.toplink.indirection.ValueHolderInterface;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.ContactInfo;

public class DeliveryOrder extends Entity {

	private ExpressCompany company = ExpressCompany.values()[0];

	private int status;

	public static enum Status {
		CREATE, DELIVERED;
	}

	private String number;

	private Date createDate;

	private ValueHolderInterface items;

	private ContactInfo contact;

	public DeliveryOrder() {
		contact = new ContactInfo();
		createDate = new Date();  
		items = new ValueHolder(new Vector());
		setValid(true);
	}

	public void deliver() {

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
	
//	public void setItems(List<DeliveryItem> items) {
//		this.items.setValue(items);
//		for (DeliveryItem item : items)
//			item.setHeader(this);
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ContactInfo getContact() {
		return contact;
	}

}
