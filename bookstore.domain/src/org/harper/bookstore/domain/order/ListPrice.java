package org.harper.bookstore.domain.order;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;

public class ListPrice {

	private Book book;

	private BigDecimal price;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
