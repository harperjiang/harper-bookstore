package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SAPReportResultBean extends ReportBean {

	private BigDecimal selling;

	private BigDecimal profit;

	private BigDecimal profitRate;

	private List<SAPData> datas;

	public List<SAPData> getDatas() {
		return datas;
	}

	public void setDatas(List<SAPData> datas) {
		this.datas = datas;
		updateProfit();
	}

	public BigDecimal getSelling() {
		return selling;
	}

	public void setSelling(BigDecimal selling) {
		this.selling = selling;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	protected void updateProfit() {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal profit = BigDecimal.ZERO;
		for (SAPData data : datas) {
			total = total.add(data.getSelling());
			profit = profit.add(data.getProfit());
		}
		setSelling(total);
		setProfit(profit);
		setProfitRate(profit.divide(total, 4, BigDecimal.ROUND_HALF_UP));
	}

	public static class SAPData {

		private BigDecimal selling;

		private BigDecimal profit;

		private Date time;

		public SAPData(Date t, BigDecimal s, BigDecimal p) {
			this.time = t;
			this.selling = s;
			this.profit = p;
		}

		public BigDecimal getSelling() {
			return selling;
		}

		public void setSelling(BigDecimal selling) {
			this.selling = selling;
		}

		public BigDecimal getProfit() {
			return profit;
		}

		public void setProfit(BigDecimal profit) {
			this.profit = profit;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public BigDecimal getProfitRate() {
			return getProfit()
					.divide(getSelling(), 4, BigDecimal.ROUND_HALF_UP);
		}
	}
}
