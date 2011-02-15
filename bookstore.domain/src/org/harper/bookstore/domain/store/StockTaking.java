package org.harper.bookstore.domain.store;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.frm.ValidateException;

public class StockTaking extends Entity {

	public static enum Status {
		NEW, DRAFT, CONFIRM;
	}

	private StoreSite site;

	private int status;

	private String number;

	private Date createDate;

	private List<StockTakingItem> items;

	public StockTaking() {
		setStatus(Status.NEW.ordinal());
	}

	public void create() {
		Validate.isTrue(Status.NEW.ordinal() == getStatus());
		setStatus(Status.DRAFT.ordinal());
		setCreateDate(new Date());
	}

	public void confirm() {
		Validate.isTrue(Status.DRAFT.ordinal() == getStatus());
		setStatus(Status.CONFIRM.ordinal());
		for (StockTakingItem item : items) {
			StoreEntry entry = getSite().getEntry(item.getBook());
			if (null == entry) {
				getSite().putInto(item.getBook(), item.getCount(),
						BigDecimal.ZERO);
			} else {
				int old = entry.getCount();
				int oldLock = entry.getCount() - entry.getAvailable();
				if (item.getCount() >= old) {
					// Add new book
					getSite().putInto(item.getBook(), item.getCount() - old,
							BigDecimal.ZERO);
				}
				if (item.getCount() < old && item.getCount() > oldLock) {
					// Decrease Total and Available
					getSite().lock(item.getBook(), old - item.getCount());
					getSite().retrieve(item.getBook(), old - item.getCount());
				}
				if (item.getCount() < oldLock) {
					// Available become not available, the draft order must be
					// cancelled first
					throw ValidateException.cancelDraftOrder(item.getBook(),
							oldLock - item.getCount());
				}
			}
		}
	}

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
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

	public List<StockTakingItem> getItems() {
		return items;
	}

	public void setItems(List<StockTakingItem> items) {
		this.items = items;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
