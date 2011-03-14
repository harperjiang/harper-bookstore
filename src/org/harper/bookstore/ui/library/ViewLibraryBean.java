package org.harper.bookstore.ui.library;

import java.util.List;

import org.harper.bookstore.service.bean.LibraryReportBean;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ViewLibraryBean extends AbstractBean {

	private String bookNameOrIsbn;

	private String borrowerInfo;

	private List<LibraryReportBean> entries;

	public String getBookNameOrIsbn() {
		return bookNameOrIsbn;
	}

	public void setBookNameOrIsbn(String bookNameOrIsbn) {
		this.bookNameOrIsbn = bookNameOrIsbn;
	}

	public List<LibraryReportBean> getEntries() {
		return entries;
	}

	public String getBorrowerInfo() {
		return borrowerInfo;
	}

	public void setBorrowerInfo(String borrowerInfo) {
		this.borrowerInfo = borrowerInfo;
	}

	public void setEntries(List<LibraryReportBean> entries) {
		List<LibraryReportBean> oldEntries = getEntries();
		this.entries = entries;
		firePropertyChange("entries", oldEntries, entries);
	}

}
