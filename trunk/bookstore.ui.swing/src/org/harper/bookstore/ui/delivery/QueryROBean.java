package org.harper.bookstore.ui.delivery;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.deliver.ReceiveOrder;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class QueryROBean extends AbstractBean {

	private Date fromCreateDate;

	private Date toCreateDate;

	private Date fromReceiveDate;

	private Date toReceiveDate;

	private String senderName;

	private String number;

	private ReceiveOrder.Status status;

	private List<ReceiveOrder> orders;

	public QueryROBean() {
		toCreateDate = Utilities.getEndOfDate();
		fromCreateDate = Utilities.getBeginOfDate(3);
	}

	public Date getFromCreateDate() {
		return fromCreateDate;
	}

	public void setFromCreateDate(Date fromCreateDate) {
		this.fromCreateDate = fromCreateDate;
	}

	public Date getToCreateDate() {
		return toCreateDate;
	}

	public void setToCreateDate(Date toCreateDate) {
		this.toCreateDate = toCreateDate;
	}

	public Date getFromReceiveDate() {
		return fromReceiveDate;
	}

	public void setFromReceiveDate(Date fromReceiveDate) {
		this.fromReceiveDate = fromReceiveDate;
	}

	public Date getToReceiveDate() {
		return toReceiveDate;
	}

	public void setToReceiveDate(Date toReceiveDate) {
		this.toReceiveDate = toReceiveDate;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public ReceiveOrder.Status getStatus() {
		return status;
	}

	public void setStatus(ReceiveOrder.Status status) {
		this.status = status;
	}

	public List<ReceiveOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ReceiveOrder> orders) {
		this.orders = orders;
	}

}
