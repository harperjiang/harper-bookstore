package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;
import java.util.Date;

public class SellAndProfitResultBean extends ReportBean {

	private BigDecimal selling;

	private BigDecimal profit;

	private Date time;

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
