package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SAPReportResultBean extends ReportBean {

	private List<SAPData> datas;

	public List<SAPData> getDatas() {
		return datas;
	}

	public void setDatas(List<SAPData> datas) {
		this.datas = datas;
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
