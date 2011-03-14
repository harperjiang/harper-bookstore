package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;

public class BookBean implements Comparable<BookBean> {

	private Book book;

	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getDesc() {
		return book.getDesc();
	}

	public String getIsbn() {
		return book.getIsbn();
	}

	public String getName() {
		return book.getName();
	}

	public void setDesc(String desc) {
		book.setDesc(desc);
	}

	public void setIsbn(String isbn) {
		book.setIsbn(isbn);
	}

	public void setName(String name) {
		book.setName(name);
	}

	@Override
	public int compareTo(BookBean o) {
		if (null == getBook() || null == o.getBook()
				|| null == getBook().getIsbn() || null == o.getBook().getIsbn())
			return 0;
		return getBook().getIsbn().compareTo(o.getBook().getIsbn());
	}
}
