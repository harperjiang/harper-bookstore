package org.harper.bookstore.domain.store;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;

public class StockAlert extends Entity {

	private StoreSite site;

	private Book book;

	private int warnThreshold;

	private int errorThreshold;

	public StoreSite getSite() {
		return site;
	}

	public void setSite(StoreSite site) {
		this.site = site;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getWarnThreshold() {
		return warnThreshold;
	}

	public void setWarnThreshold(int warnThreshold) {
		this.warnThreshold = warnThreshold;
	}

	public int getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(int errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

	

}
