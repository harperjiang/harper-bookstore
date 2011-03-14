package org.harper.bookstore.ui.report;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.harper.bookstore.service.bean.PurchaseReportItem;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class PurchaseReportBean extends AbstractBean {

	private Date startDate = DateUtils.addDays(new Date(), -7);

	private Date stopDate = new Date();

	private List<PurchaseReportItem> result;

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

	public List<PurchaseReportItem> getResult() {
		return result;
	}

	public void setResult(List<PurchaseReportItem> result) {
		List<PurchaseReportItem> oldItems = getResult();
		this.result = result;
		firePropertyChange("result", oldItems, result);
	}

}
