package org.harper.bookstore.domain;

import org.harper.bookstore.domain.profile.Book;

public interface Item {

	public Book getBook();
	
	public void setBook(Book book);

	public int getCount();

	public void setCount(int count);
}
