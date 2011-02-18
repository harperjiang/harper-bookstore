package org.harper.bookstore.ui.delivery;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class QueryDOBean extends AbstractBean {

	private Date fromDate;

	private Date toDate;

	private String consigneeName;

	private String poNumber;

	private String poCustomerId;

	private List<DeliveryOrder> orders;

	public QueryDOBean() {
		toDate = new Date();
		fromDate = new Date(toDate.getTime() - 3600000 * 24 * 7);
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoCustomerId() {
		return poCustomerId;
	}

	public void setPoCustomerId(String poCustomerId) {
		this.poCustomerId = poCustomerId;
	}

	public List<DeliveryOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<DeliveryOrder> orders) {
		Object value = getOrders();
		this.orders = orders;
		firePropertyChange("orders", value, orders);
	}

}
