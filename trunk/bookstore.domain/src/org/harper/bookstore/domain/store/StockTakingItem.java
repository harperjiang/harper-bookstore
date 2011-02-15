package org.harper.bookstore.domain.store;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;

public class StockTakingItem extends Entity {

	private Book book;

	private int count;

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

}
