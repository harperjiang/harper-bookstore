package org.harper.bookstore.ui.library;

import java.util.List;

import org.harper.bookstore.domain.profile.Borrower;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class FindBorrowerBean extends AbstractBean {

	private String name;

	private String company;

	private List<Borrower> borrowers;

	private Borrower selected;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<Borrower> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(List<Borrower> borrowers) {
		List<Borrower> oldBorrowers = getBorrowers();
		this.borrowers = borrowers;
		firePropertyChange("borrowers", oldBorrowers, borrowers);
	}

	public Borrower getSelected() {
		return selected;
	}

	public void setSelected(Borrower selected) {
		this.selected = selected;
	}

}
