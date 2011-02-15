package org.harper.bookstore.ui.profile;

import org.harper.frm.gui.swing.manager.AbstractBean;

public class NewBookBean extends AbstractBean {

	private String isbn;
	
	private String bookName;
	
	private String desc;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
