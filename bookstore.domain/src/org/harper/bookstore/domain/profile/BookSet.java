package org.harper.bookstore.domain.profile;

import java.util.ArrayList;
import java.util.List;

public class BookSet extends Book {

	private List<BookUnit> books;

	public BookSet() {
		super();
		books = new ArrayList<BookUnit>();
	}

	public List<BookUnit> getBooks() {
		return books;
	}

	public void setBooks(List<BookUnit> books) {
		this.books.clear();
		for (BookUnit b : books) {
			BookUnit2 bu2 = new BookUnit2(b.getBook(), b.getCount(), b
					.getUnitPrice());
			bu2.setParent(this);
			this.books.add(bu2);
		}
	}

	public List<BookUnit> getContent() {
		return books;
	}
}
