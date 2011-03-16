package org.harper.bookstore.domain.deliver;

import java.math.BigDecimal;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.profile.Book;

public class ReceiveItem extends Entity implements Item {

	private ReceiveOrder header;

	private Book book;

	private int count;

	private BigDecimal unitCost;

	public ReceiveOrder getHeader() {
		return header;
	}

	public void setHeader(ReceiveOrder header) {
		this.header = header;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

}
