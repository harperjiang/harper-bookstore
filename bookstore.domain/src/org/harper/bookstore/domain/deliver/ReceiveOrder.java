package org.harper.bookstore.domain.deliver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.store.StoreSite;

public class ReceiveOrder extends Entity {
	public static enum ReceiveType {
		RETURN/* 退货 */, RECEIVE;
		/* 到货 */

		public String desc() {
			ResourceBundle rb = ResourceBundle
					.getBundle("org/harper/bookstore/domain/deliver/ReceiveType");
			return rb.getString(name());
		}
	}

	public static enum Status {
		DRAFT, CONFIRM;
	}

	private ExpressCompany company;

	private DeliveryOrder delivery;

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

	public void confirm() {
		Validate.isTrue(Status.DRAFT == getStatus());
		setStatus(Status.CONFIRM);
		setReceiveDate(new Date());
		if (ReceiveType.RETURN.equals(getType())) {
			Validate.notNull(getDelivery());

			Map<Book, DeliveryItem> bookToItem = new HashMap<Book, DeliveryItem>();
			for (DeliveryItem di : getDelivery().getItems()) {
				bookToItem.put(di.getBook(), di);
			}
			// TODO
			for (ReceiveItem ri : getItems()) {
				if (!bookToItem.containsKey(ri.getBook())) {
					throw new IllegalArgumentException(MessageFormat.format(
							"No such book:{0}", ri.getBook().getName()));
				}
				DeliveryItem di = bookToItem.get(ri.getBook());
				di.setReturned(di.getReturned() + ri.getCount());
				Validate.isTrue(di.getCount() >= di.getReturned(),
						"Too many return");
				// Return to Site
				getSite()
						.putInto(ri.getBook(), ri.getCount(), ri.getUnitCost());
			}
		}
	}

	public DeliveryOrder getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryOrder delivery) {
		this.delivery = delivery;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Status getStatus() {
		return Status.values()[status];
	}

	public void setStatus(Status status) {
		this.status = status.ordinal();
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

	public ReceiveType getType() {
		return ReceiveType.valueOf(type);
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
