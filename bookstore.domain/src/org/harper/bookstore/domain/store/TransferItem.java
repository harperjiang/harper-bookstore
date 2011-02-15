package org.harper.bookstore.domain.store;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;

public class TransferItem extends Entity {

	private int oid;

	private Book book;

	private int count;

	private Transfer header;

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

	public Transfer getHeader() {
		return header;
	}

	public void setHeader(Transfer header) {
		this.header = header;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

}
