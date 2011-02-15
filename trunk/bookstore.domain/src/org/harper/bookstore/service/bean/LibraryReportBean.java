package org.harper.bookstore.service.bean;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Borrower;

public class LibraryReportBean {

	private Book book;

	private Borrower borrower;

	private int outstanding;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public String getBorrowerDesc() {
		return getBorrower().getName();
	}
}
