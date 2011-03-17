package org.harper.bookstore.ui.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.service.bean.report.ProfitRateResultBean.ProfitRateItemBean;
import org.harper.frm.gui.swing.freechart.PieData;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ProfitRateReportBean extends AbstractBean {

	private Date fromDate;

	private Date toDate;

	private List<PieData> pieDatas;

	private List<ProfitRateItemBean> items;

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

	public List<PieData> getPieDatas() {
		return pieDatas;
	}

	public void setPieDatas(List<PieData> pieDatas) {
		List<PieData> old = getPieDatas();
		this.pieDatas = pieDatas;
		firePropertyChange("pieDatas", old, pieDatas);
	}

	public List<ProfitRateItemBean> getItems() {
		return items;
	}

	public void setItems(List<ProfitRateItemBean> items) {
		List<ProfitRateItemBean> old = getItems();
		this.items = items;
		firePropertyChange("items", old, items);
		List<PieData> pies = new ArrayList<PieData>();
		for (ProfitRateItemBean item : items)
			pies.add(new ProfitPieData(item));
		setPieDatas(pies);
	}

	public static class ProfitPieData implements PieData {
		private ProfitRateItemBean item;

		public ProfitPieData(ProfitRateItemBean bean) {
			this.item = bean;
		}

		@Override
		public String getKey() {
			return item.getRangeDesc();
		}

		@Override
		public Number getValue() {
			return Integer.valueOf(item.getCount());
		}
	}
}
