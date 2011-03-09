package org.harper.bookstore.ui.store;

import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class QueryStockTakingBean extends AbstractBean {

	private Date from = Utilities.getBeginOfDate(15);

	private Date to = Utilities.getEndOfDate();

	private StockTaking.Status status;

	private StoreSite site;

	private List<StockTaking> result;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public StockTaking.Status getStatus() {
		return status;
	}

	public void setStatus(StockTaking.Status status) {
		this.status = status;
	}

	public List<StockTaking> getResult() {
		return result;
	}

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

	public void setResult(List<StockTaking> result) {
		List<StockTaking> old = getResult();
		this.result = result;
		firePropertyChange(new PropertyChangeEvent(this, "result", old,
				this.result));
	}

}
