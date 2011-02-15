package org.harper.bookstore.domain.order;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StoreEntry;

public class CollectItem {

	private Book book;

	private int count;

	private StoreEntry siteEntry;

	private CollectPlan header;

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

	public CollectPlan getHeader() {
		return header;
	}

	public void setHeader(CollectPlan header) {
		this.header = header;
	}

	protected StoreEntry getSiteEntry() {
		return siteEntry;
	}

	protected void setSiteEntry(StoreEntry siteEntry) {
		this.siteEntry = siteEntry;
	}

}
