package org.harper.bookstore.domain.store;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.domain.profile.Book;

public class StockAlert extends Entity {

	private StoreSite site;

	private Book book;

	private int warnThreshold;

	private int errorThreshold;

	protected StoreSite getSite() {
		return site;
	}

	protected void setSite(StoreSite site) {
		this.site = site;
	}

	protected Book getBook() {
		return book;
	}

	protected void setBook(Book book) {
		this.book = book;
	}

	protected int getWarnThreshold() {
		return warnThreshold;
	}

	protected void setWarnThreshold(int warnThreshold) {
		this.warnThreshold = warnThreshold;
	}

	protected int getErrorThreshold() {
		return errorThreshold;
	}

	protected void setErrorThreshold(int errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

}
