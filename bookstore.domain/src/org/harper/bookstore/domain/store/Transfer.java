package org.harper.bookstore.domain.store;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.BookUnit;

public class Transfer extends Entity {

	public static enum Status {
		NEW, DRAFT, CONFIRM, CANCEL
	};

	private int oid;

	private String number;

	private Date createDate;

	private Date expectActionDate;

	private Date actionDate;

	private StoreSite fromSite;

	private StoreSite toSite;

	private int status;

	private List<TransferItem> items;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
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

	public Date getExpectActionDate() {
		return expectActionDate;
	}

	public void setExpectActionDate(Date expectActionDate) {
		this.expectActionDate = expectActionDate;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public List<TransferItem> getItems() {
		return items;
	}

	public void setItems(List<TransferItem> items) {
		for (TransferItem item : items)
			item.setHeader(this);
		this.items = items;
	}

	public StoreSite getFromSite() {
		return fromSite;
	}

	public void setFromSite(StoreSite fromSite) {
		this.fromSite = fromSite;
	}

	public StoreSite getToSite() {
		return toSite;
	}

	public void setToSite(StoreSite toSite) {
		this.toSite = toSite;
	}

	public Status getStatus() {
		return Status.values()[status];
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		return getStatus().name();
	}

	public void plan() {
		Validate.isTrue(status == Status.NEW.ordinal());
		status = Status.DRAFT.ordinal();
		Validate.notNull(getFromSite());
		Validate.notNull(getToSite());
		setCreateDate(new Date());
		lockStore();
	}

	public void lockStore() {
		for (TransferItem item : items)
			getFromSite().lock(item.getBook(), item.getCount());
	}

	public void releaseStore() {
		for (TransferItem item : items)
			getFromSite().cancel(item.getBook(), item.getCount());
	}

	public void confirm() {
		Validate.isTrue(status == Status.DRAFT.ordinal());
		status = Status.CONFIRM.ordinal();
		setActionDate(new Date());
		for (TransferItem item : items) {
//			getFromSite().lock(item.getBook(), item.getCount());
			BookUnit retrieved = getFromSite().retrieve(item.getBook(),
					item.getCount());
			getToSite().putInto(retrieved.getBook(), retrieved.getCount(),
					retrieved.getUnitPrice());
		}
	}

	public void cancel() {
		Validate.isTrue(status == Status.DRAFT.ordinal());
		status = Status.CANCEL.ordinal();
		releaseStore();
	}

}
