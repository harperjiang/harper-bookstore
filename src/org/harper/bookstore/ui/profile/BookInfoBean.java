package org.harper.bookstore.ui.profile;

import java.util.List;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class BookInfoBean extends AbstractBean {

	private List<BookBean> books;

	public List<BookBean> getBooks() {
		return books;
	}

	public void setBooks(List<BookBean> books) {
		List<BookBean> oldBook = getBooks();
		this.books = books;
		firePropertyChange("books", oldBook, books);
	}

	
}
