package org.harper.bookstore.ui.common;

import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ISBNChooseBean extends AbstractBean {

	private List<Book> bookList;

	private List<Book> selected;

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		List<Book> oldList = getBookList();
		this.bookList = bookList;
		firePropertyChange("bookList", oldList, bookList);
	}

	public List<Book> getSelected() {
		return selected;
	}

	public void setSelected(List<Book> selected) {
		this.selected = selected;
	}

}
