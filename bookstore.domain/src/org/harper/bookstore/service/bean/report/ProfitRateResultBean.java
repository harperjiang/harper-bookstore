package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.util.Utilities;

public class ProfitRateResultBean extends ReportBean {

	private List<ProfitRateItemBean> items;

	private int totalCount;

	public ProfitRateResultBean() {
		super();
		items = new ArrayList<ProfitRateItemBean>();
	}

	public List<ProfitRateItemBean> getItems() {
		return items;
	}

	public void addItem(ProfitRateItemBean item) {
		this.items.add(item);
		updatePercentage();
	}

	protected void updatePercentage() {
		int total = 0;
		for (ProfitRateItemBean item : items)
			total += item.getCount();
		BigDecimal tt = new BigDecimal(total);
		for (ProfitRateItemBean item : items)
			item.setPercentage(new BigDecimal(item.getCount()).divide(tt, 4,
					BigDecimal.ROUND_HALF_UP));
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public static class ProfitRateItemBean {

		private int count;

		private BigDecimal percentage;

		private BigDecimal floor;

		private BigDecimal ceiling;

		public ProfitRateItemBean(int c, BigDecimal f, BigDecimal ce) {
			this.count = c;
			this.floor = f;
			this.ceiling = ce;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public BigDecimal getPercentage() {
			return percentage;
		}

		protected void setPercentage(BigDecimal percentage) {
			this.percentage = percentage;
		}

		public BigDecimal getFloor() {
			return floor;
		}

		public void setFloor(BigDecimal floor) {
			this.floor = floor;
		}

		public BigDecimal getCeiling() {
			return ceiling;
		}

		public void setCeiling(BigDecimal ceiling) {
			this.ceiling = ceiling;
		}

		public String getRangeDesc() {
			return MessageFormat.format("{0}-{1}",
					Utilities.percentage(getFloor()),
					Utilities.percentage(getCeiling()));
		}

	}
}
