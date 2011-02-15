package org.harper.bookstore.domain.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Borrower;
import org.harper.bookstore.domain.store.StoreSite;

public class Record extends Entity {

	public static enum Status {
		NEW, OST, PARTIAL_OST, MATCH_CLOSE, FORCE_CLOSE;
	};

	private StoreSite site;

	private String number;

	private Borrower borrower;

	private Date accountDate;

	private Date closeDate;

	private int status;

	private List<RecordItem> items;

	private String remark;

	private String closeReason;

	public Record() {
		super();
		items = new ArrayList<RecordItem>();
	}

	protected void updateStatus() {
		if (Status.FORCE_CLOSE.ordinal() == getStatus())
			return;
		int total = 0;
		int ost = 0;
		for (RecordItem item : getItems()) {
			if (RecordItem.Status.ERROR.ordinal() == item.getStatus())
				continue;
			total += item.getCount();
			ost += item.getOutstanding();
		}
		if (total == ost)
			setStatus(Status.OST.ordinal());
		else if (ost == 0)
			setStatus(Status.MATCH_CLOSE.ordinal());
		else
			setStatus(Status.PARTIAL_OST.ordinal());
	}

	public void forceClose(String reason) {
		setStatus(Status.FORCE_CLOSE.ordinal());
		setCloseDate(new Date());
		setCloseReason(reason);
		for (RecordItem item : getItems()) {
			if (item.isOutstanding())
				item.setStatus(RecordItem.Status.ERROR.ordinal());
		}
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
		setCloseDate(accountDate);
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<RecordItem> getItems() {
		return items;
	}

	public void setItems(List<RecordItem> items) {
		this.items = items;
		if (null != items)
			for (RecordItem item : items)
				item.setHeader(this);
	}

	public void addItem(RecordItem item) {
		item.setHeader(this);
		items.add(item);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

}
