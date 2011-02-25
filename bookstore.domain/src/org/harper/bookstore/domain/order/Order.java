package org.harper.bookstore.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.frm.core.tools.sort.HeapSorter;
import org.springframework.util.CollectionUtils;

public abstract class Order extends Entity {

	public static enum Status {
		NEW, DRAFT, CONFIRM, FINISH, CANCEL;
	}

	private int status;

	private String number;

	private List<OrderItem> items;

	private Date createDate;

	// Customer Remark
	private String remark;

	// My Remark;
	private String memo;

	private String feeName;

	private BigDecimal feeAmount;

	private String refno;

	private StoreSite site;

	private OrderContact contact;

	private BigDecimal totalAmt;

	public Order() {
		super();
		createDate = new Date();
		contact = new OrderContact();
		contact.setOrder(this);
		items = new ArrayList<OrderItem>();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<OrderItem> getItems() {
		if (null == items)
			items = new ArrayList<OrderItem>();
		return new HeapSorter().sort(items, new String[] { "book.isbn" },
				new boolean[] { true });

	}

	public void addItem(OrderItem item) {
		this.items.add(item);
		item.setOrder(this);
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
		if (null != items)
			for (OrderItem item : items)
				item.setOrder(this);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public OrderItem getItem(Book book) {
		if (null == items)
			return null;
		for (OrderItem item : items) {
			if (book.equals(item.getBook()))
				return item;
		}
		return null;
	}

	// Always calculate by item, not including transportation fee
	public BigDecimal getSubtotal() {
		BigDecimal sum = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(items))
			for (OrderItem item : items) {
				if (null != item.getUnitPrice())
					sum = sum.add(item.getUnitPrice().multiply(
							new BigDecimal(item.getCount())));
			}
		return sum;
	}

	public BigDecimal getTotal() {
		if ((Order.Status.NEW.ordinal() == getStatus() || Order.Status.DRAFT
				.ordinal() == getStatus()) && StringUtils.isEmpty(refno)) {
			BigDecimal sum = BigDecimal.ZERO;
			if (!CollectionUtils.isEmpty(items))
				for (OrderItem item : items) {
					if (null != item.getUnitPrice())
						sum = sum.add(item.getUnitPrice().multiply(
								new BigDecimal(item.getCount())));
				}
			if (null != feeAmount)
				sum = sum.add(feeAmount);
			setTotalAmt(sum);
			return sum;
		}
		return totalAmt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

	public ContactInfo getContact() {
		if (null == contact) {
			contact = new OrderContact();
			contact.setOrder(this);
		}
		return contact;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

}
