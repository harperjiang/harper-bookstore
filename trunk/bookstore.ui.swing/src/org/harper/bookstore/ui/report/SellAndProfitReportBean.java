package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.service.bean.report.SellAndProfitResultBean.SellAndProfitData;
import org.harper.frm.gui.swing.freechart.CategoryData;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class SellAndProfitReportBean extends AbstractBean {

	public static class SellAndProfitCategoryData implements CategoryData {

		private SellAndProfitData data;

		private boolean sell;

		public SellAndProfitCategoryData(SellAndProfitData data, boolean sell) {
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
			return sell ? data.getSelling() : data.getProfit();
		}
	}

	private Date fromDate;

	private Date toDate;

	private List<SellAndProfitCategoryData> datas;

	private List<SellAndProfitData> originDatas;

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

	public List<SellAndProfitCategoryData> getDatas() {
		return datas;
	}

	public void setDatas(List<SellAndProfitCategoryData> datas) {
		List<SellAndProfitCategoryData> old = getDatas();
		this.datas = datas;
		firePropertyChange("datas", old, datas);
	}

	public List<SellAndProfitData> getOriginDatas() {
		return originDatas;
	}

	public void setOriginDatas(List<SellAndProfitData> originDatas) {
		List<SellAndProfitData> old = getOriginDatas();
		this.originDatas = originDatas;
		firePropertyChange("originDatas", old, originDatas);

		List<SellAndProfitCategoryData> result = new ArrayList<SellAndProfitCategoryData>();
		for (SellAndProfitData sapd : originDatas) {
			result.add(new SellAndProfitCategoryData(sapd, true));
			result.add(new SellAndProfitCategoryData(sapd, false));
		}
		setDatas(result);
	}

}
