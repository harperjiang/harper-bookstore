package org.harper.bookstore.service.bean.report;

public class ProfitRateResultBean extends ReportBean {

	private ProfitRateItemBean[] items;

	private int totalCount;
	
	public ProfitRateItemBean[] getItems() {
		return items;
	}

	public void setItems(ProfitRateItemBean[] items) {
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
