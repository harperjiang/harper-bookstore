package org.harper.bookstore.domain.profile;

import java.math.BigDecimal;


public class BookUnit {
	private Book book;

	private BigDecimal unitPrice;

	private int count;

	public BookUnit(Book book, int count, BigDecimal up) {
		super();
		this.book = book;
		this.unitPrice = up;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getSum() {
		return getUnitPrice().multiply(new BigDecimal(getCount()));
	}
}
