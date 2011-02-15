package org.harper.bookstore.domain.deliver;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;

public class DeliveryItem extends Entity implements Item {

	private DeliveryOrder header;

	private OrderItem orderItem;

	private int count;

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
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public Book getBook() {
		return getOrderItem().getBook();
	}

	@Override
	public void setBook(Book book) {
		getOrderItem().setBook(book);
	}

}
