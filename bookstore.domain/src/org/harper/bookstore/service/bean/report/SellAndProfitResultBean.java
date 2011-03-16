package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SellAndProfitResultBean extends ReportBean {

	public static class SellAndProfitData {

		private BigDecimal selling;

		private BigDecimal profit;

		private Date time;

		public SellAndProfitData(Date t, BigDecimal s, BigDecimal p) {
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
	}

	private List<SellAndProfitData> datas;

	public List<SellAndProfitData> getDatas() {
		return datas;
	}

	public void setDatas(List<SellAndProfitData> datas) {
		this.datas = datas;
	}

}
