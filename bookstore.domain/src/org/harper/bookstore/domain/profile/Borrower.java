package org.harper.bookstore.domain.profile;

import java.util.Collection;

import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.library.ReturnRecord;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.RepoFactory;

public class Borrower extends Counterpart {

	public Borrower() {
		super();
	}
	
	public BorrowRecord borrow() {
		BorrowRecord record = new BorrowRecord();
		record.setBorrower(this);
		record.setNumber(RepoFactory.INSTANCE.getCommonRepo().getNumber(
				CommonRepo.NUMBER_TYPE_BR));
		return record;
	}
	
	public ReturnRecord recordReturn() {
		ReturnRecord record = new ReturnRecord();
		record.setBorrower(this);
		record.setNumber(RepoFactory.INSTANCE.getCommonRepo().getNumber(
				CommonRepo.NUMBER_TYPE_BR));
		return record;
	}
	
	public void addBook(Collection<RecordItem> items) {
		
	}
}
