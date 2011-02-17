package org.harper.bookstore.domain.order;

import java.math.BigDecimal;

import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.profile.Book;

public class OrderItem implements Item {

	private int oid;

	private Book book;

	private int count;

	private BigDecimal unitCost;

	private BigDecimal unitPrice;

	private BigDecimal totalAmount;

	private Order order;

	private boolean agent;

	private String refUid;

	private int sentCount;

	public OrderItem() {
		super();
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public boolean isAgent() {
		return agent;
	}

	public void setAgent(boolean isAgent) {
		this.agent = isAgent;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRefUid() {
		return refUid;
	}

	public void setRefUid(String refUid) {
		this.refUid = refUid;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}
	
	public int getUnsentCount() {
		return getCount() - getSentCount();
	}
}
