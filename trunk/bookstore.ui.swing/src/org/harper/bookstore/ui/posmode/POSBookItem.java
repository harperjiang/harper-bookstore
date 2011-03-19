package org.harper.bookstore.ui.posmode;

import java.io.Serializable;

import org.harper.bookstore.domain.profile.Book;

public class POSBookItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836711265725197409L;

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
