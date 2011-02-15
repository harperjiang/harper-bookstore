package org.harper.bookstore.ui.profile;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class BookSetBean extends AbstractBean {

	private String isbn;

	private String name;

	private List<Book> books;

	public BookSetBean() {
		books = new ArrayList<Book>();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Book> getBooks() {
		return books;
	}

}
