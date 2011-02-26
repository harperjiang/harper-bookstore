package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;

public class BookSetItem {

	private Book book;

	private BigDecimal percentage;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

}
