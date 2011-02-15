package org.harper.bookstore.domain.deliver;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.order.OrderItem;

public class DeliveryItem extends Entity {

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

}
