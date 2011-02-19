package org.harper.bookstore.domain.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.harper.bookstore.domain.Entity;

public class DisplayItem extends Entity {

	private String name;

	private int count;

	private BigDecimal unitPrice;

	private BigDecimal actualPrice;

	private PurchaseOrder order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
	}

	public BigDecimal getDiscount() {
		return getActualPrice().divide(
				getUnitPrice().multiply(new BigDecimal(count)), 4,
				RoundingMode.HALF_UP);
	}
}
