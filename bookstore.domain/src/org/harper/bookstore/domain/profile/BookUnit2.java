package org.harper.bookstore.domain.profile;

import java.math.BigDecimal;

public class BookUnit2 extends BookUnit {

	public BookUnit2() {
		super(null,0,null);
	}
	
	public BookUnit2(Book book, int count, BigDecimal up) {
		super(book, count, up);
		// TODO Auto-generated constructor stub
	}

	private BookSet parent;

	public BookSet getParent() {
		return parent;
	}

	public void setParent(BookSet parent) {
		this.parent = parent;
	}

}
