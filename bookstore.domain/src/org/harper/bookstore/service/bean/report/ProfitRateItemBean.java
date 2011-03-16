package org.harper.bookstore.service.bean.report;

import java.math.BigDecimal;

public class ProfitRateItemBean {

	private int count;

	private BigDecimal percentage;

	private BigDecimal floor;

	private BigDecimal ceiling;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
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

}
