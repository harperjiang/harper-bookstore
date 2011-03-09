package org.harper.bookstore.domain.store;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.frm.core.tools.sort.HeapSorter;
import org.springframework.util.CollectionUtils;

public class StockTaking extends Entity {

	public static enum Status {
		DRAFT, CONFIRM, CANCEL;
	}

	private StoreSite site;

	private int status;

	private String number;

	private Date createDate = new Date();

	private Date confirmDate;

	private BigDecimal discrepancy;

	private String remark;

	private List<StockTakingItem> items;

	public StockTaking() {
		setStatus(Status.DRAFT.ordinal());
	}

	public void create() {
		Validate.isTrue(0 == getOid());
		Validate.isTrue(Status.DRAFT.ordinal() == getStatus());
		setStatus(Status.DRAFT.ordinal());
		if (null == getCreateDate())
			setCreateDate(new Date());
	}

	public void confirm() {
		Validate.isTrue(Status.DRAFT.ordinal() == getStatus());
		setStatus(Status.CONFIRM.ordinal());
		if (null == getConfirmDate())
			setConfirmDate(new Date());
		for (StockTakingItem item : items) {
			StoreEntry entry = getSite().getEntry(item.getBook());
			if (null == entry) {
				getSite().putInto(item.getBook(), item.getCurrentCount(),
						BigDecimal.ZERO);
			} else {
				if (item.getCurrentCount() > item.getOriginCount()) {
					// 盘点时发现库增
					getSite().putInto(item.getBook(),
							item.getCurrentCount() - item.getOriginCount(),
							item.getUnitPrice());
				}
				if (item.getCurrentCount() < item.getOriginCount()) {
					// 盘点时发现库损
					getSite().lock(item.getBook(),
							item.getOriginCount() - item.getCurrentCount());
					getSite().retrieve(item.getBook(),
							item.getOriginCount() - item.getCurrentCount());
				}
			}
		}
	}

	public void cancel() {
		Validate.isTrue(Status.DRAFT.ordinal() == getStatus());
		setStatus(Status.CANCEL.ordinal());
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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
		if (CollectionUtils.isEmpty(items))
			return items;
		return new HeapSorter(true).sort(items, new String[] { "book.isbn" },
				new boolean[] { true });
	}

	public void addItem(StockTakingItem item) {
		item.setHeader(this);
		this.getItems().add(item);
	}

	public void setItems(List<StockTakingItem> items) {
		this.items = items;
		for (StockTakingItem item : items)
			item.setHeader(this);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusStr() {
		return Status.values()[getStatus()].name();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getDiscrepancy() {
		return discrepancy;
	}

	public void setDiscrepancy(BigDecimal discrepancy) {
		this.discrepancy = discrepancy;
	}

	public Date getDate() {
		return null != confirmDate ? confirmDate : createDate;
	}

}
