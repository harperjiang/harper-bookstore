package org.harper.bookstore.ui.profile;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.frm.core.tools.sort.HeapSorter;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class BookSetBean extends AbstractBean {

	private BookSet set;

	private String isbn;

	private String name;

	private List<BookSetItem> books;

	public BookSetBean() {
		books = new ArrayList<BookSetItem>();
	}

	public BookSetBean(BookSet set) {
		this();
		this.set = set;
		this.isbn = set.getIsbn();
		this.name = set.getName();
		for (BookUnit unit : new HeapSorter().sort(set.getBooks(),
				new String[] { "book.isbn" }, new boolean[] { true })) {
			BookSetItem item = new BookSetItem();
			item.setBook(unit.getBook());
			item.setPercentage(unit.getUnitPrice());
			books.add(item);
		}
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

	public void setBooks(List<BookSetItem> books) {
		this.books = books;
	}

	public List<BookSetItem> getBooks() {
		return books;
	}

	public BookSet getSet() {
		if (null == set) {
			set = new BookSet();
		} else {
			set.getBooks().clear();
		}
		set.setIsbn(isbn);
		set.setName(name);
		ArrayList<BookUnit> units = new ArrayList<BookUnit>();
		for (BookSetItem item : books) {
			BookUnit unit = new BookUnit(item.getBook(),1,item.getPercentage());
			units.add(unit);
		}
		set.setBooks(units);

		return set;
	}

	public void setSet(BookSet set) {
		this.set = set;
	}

}
