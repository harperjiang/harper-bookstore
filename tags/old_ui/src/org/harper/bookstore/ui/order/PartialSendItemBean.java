package org.harper.bookstore.ui.order;

import org.harper.bookstore.domain.profile.Book;

public class PartialSendItemBean {

	private Book book;

	private int count;

	private int send;

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

	public int getSend() {
		return send;
	}

	public void setSend(int send) {
		this.send = send;
	}

}
