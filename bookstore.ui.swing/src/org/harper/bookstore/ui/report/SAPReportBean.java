package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.service.bean.report.SAPReportResultBean;
import org.harper.bookstore.service.bean.report.SAPReportResultBean.SAPData;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.freechart.CategoryData;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class SAPReportBean extends AbstractBean {

	public static class SAPCategoryData implements CategoryData {

		private SAPData data;

		private boolean sell;

		public SAPCategoryData(SAPData data, boolean sell) {
			this.data = data;
			this.sell = sell;
		}

		@Override
		public String getCategory() {
			return sell ? "Sales" : "Profit";
		}

		@Override
		public String getX() {
			return new SimpleDateFormat("yyyyMMdd").format(data.getTime());
		}

		@Override
		public BigDecimal getY() {
			return sell ? data.getSelling().subtract(data.getProfit()) : data
					.getProfit();
		}
	}

	private Date fromDate;

	private Date toDate;

	private List<SAPCategoryData> datas;

	private List<SAPData> originDatas;

	private String summary;

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

	public List<SAPCategoryData> getDatas() {
		return datas;
	}

	public void setDatas(List<SAPCategoryData> datas) {
		List<SAPCategoryData> old = getDatas();
		this.datas = datas;
		firePropertyChange("datas", old, datas);
	}

	public List<SAPData> getOriginDatas() {
		return originDatas;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		String old = getSummary();
		this.summary = summary;
		firePropertyChange("summary", old, summary);
	}

	public void setOriginDatas(List<SAPData> originDatas) {
		List<SAPData> old = getOriginDatas();
		this.originDatas = originDatas;
		firePropertyChange("originDatas", old, originDatas);

		List<SAPCategoryData> result = new ArrayList<SAPCategoryData>();
		for (SAPData sapd : originDatas) {
			result.add(new SAPCategoryData(sapd, true));
			result.add(new SAPCategoryData(sapd, false));
		}
		setDatas(result);
	}

	public void setResult(SAPReportResultBean bean) {
		setOriginDatas(bean.getDatas());
		setSummary(MessageFormat.format(
				"Total: {0}; Profit: {1}; Profit Rate:{2}", bean.getSelling(),
				bean.getProfit(), Utilities.percentage(bean.getProfitRate())));

	}

}
