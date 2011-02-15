package org.harper.bookstore.repo;

import java.util.List;

import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.LibraryEntry;
import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Borrower;

public interface LibraryRepo {

	public Borrower findBorrower(String id);
	
	public List<Borrower> findBorrowers(String name,String company);
	
	public List<RecordItem> getItems(Borrower borrower, Book book);

	public LibraryEntry getEntry(Book book);
	
	public List<BorrowRecord> getOutstandingRecords(Borrower borrower);

	public List<LibraryEntry> findEntries(String book);

	public List<RecordItem> findOstRecord(String book,String borrower);
}
