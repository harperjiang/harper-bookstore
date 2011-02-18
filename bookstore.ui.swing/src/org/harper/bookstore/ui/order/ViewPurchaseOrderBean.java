package org.harper.bookstore.ui.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.Order.Status;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.PurchaseOrder.DeliveryStatus;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ViewPurchaseOrderBean extends AbstractBean {

	private String orderType = "PO";

	private Order.Status status = null;

	private Date startDate = DateUtils.addDays(new Date(), -7);

	private Date stopDate = new Date();

	private String partyId;

	private String orderNum;

	private PurchaseOrder.DeliveryStatus deliveryStatus = null;

	private List<PurchaseOrder> searchResults;

	public String getOrderType() {
		return orderType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public List<PurchaseOrder> getSearchResults() {
		return searchResults;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public void setSearchResults(List<PurchaseOrder> searchResults) {
		List<PurchaseOrder> oldResult = getSearchResults();
		this.searchResults = searchResults;
		firePropertyChange("searchResults", null, searchResults);
	}

}
