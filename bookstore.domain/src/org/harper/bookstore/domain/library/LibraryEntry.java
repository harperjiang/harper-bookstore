package org.harper.bookstore.domain.library;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;

public class LibraryEntry extends Entity{

	private Book book;

	private int outstanding;

	private int total;

	public void borrow(int count) {
		if (outstanding == total) {
			total+=count;
		}
		outstanding+=count;
	}

	public void ret(int count) {
		if (outstanding == 0) {
			// Add Book
			total+=count;
		} else {
			outstanding-=count;
		}
	}

	public void lose(int ost) {
		outstanding -= ost;
		total -= ost;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


}
