package org.harper.bookstore.domain.deliver;

import java.math.BigDecimal;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;

public class DeliveryItem extends Entity implements Item {

	private DeliveryOrder header;

	private OrderItem orderItem;

	private Book book;

	private int count;

	private BigDecimal unitCost;

	public DeliveryOrder getHeader() {
		return header;
	}

	public void setHeader(DeliveryOrder header) {
		this.header = header;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
		if (null != orderItem) {
			this.setBook(orderItem.getBook());
		}
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

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

}
