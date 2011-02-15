package org.harper.bookstore.ui.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.harper.bookstore.domain.order.Order;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ViewOrderBean extends AbstractBean {

	private String orderType = "PO";

	private String status = "ALL";

	private Date startDate = DateUtils.addDays(new Date(), -7);

	private Date stopDate = new Date();

	private String partyId;

	private String orderNum;

	private List<Order> searchResults;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public List<Order> getSearchResults() {
		return searchResults;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public void setSearchResults(List<Order> searchResults) {
		List<Order> oldResult = getSearchResults();
		this.searchResults = searchResults;
		firePropertyChange("searchResults", null, searchResults);
	}

}
