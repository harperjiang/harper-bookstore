package org.harper.bookstore.ui.store;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ViewTransferBean extends AbstractBean {

	private Transfer.Status status;

	private Date startDate;

	private Date stopDate;

	private StoreSite fromSite;

	private StoreSite toSite;

	private List<Transfer> items;

	public Transfer.Status getStatus() {
		return status;
	}

	public void setStatus(Transfer.Status status) {
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

	public List<Transfer> getItems() {
		return items;
	}

	public void setItems(List<Transfer> items) {
		List<Transfer> oldItems = getItems();
		this.items = items;
		firePropertyChange("items", oldItems, items);
	}

}
