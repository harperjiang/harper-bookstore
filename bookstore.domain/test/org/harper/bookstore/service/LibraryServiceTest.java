package org.harper.bookstore.service;

import java.util.Date;

import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.library.ReturnRecord;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Borrower;
import org.junit.Test;

public class LibraryServiceTest {

	@Test
	public void testBorrowBook() {
		Borrower borrower = new Borrower();
		borrower.setName("newbr");
		
		BorrowRecord rec = new BorrowRecord();
		rec.setAccountDate(new Date());
		RecordItem item = new RecordItem();
		
		Book book = new ProfileService().getBook("0001");
		item.setBook(book);
		item.setCount(10);
		
		rec.addItem(item);
		
		new LibraryService().borrowBook(borrower, rec);
	}

	@Test
	public void testReturnBook() {
		Borrower borrower = new Borrower();
		borrower.setName("newbr");
		
		ReturnRecord rec = new ReturnRecord();
		rec.setAccountDate(new Date());
		
		RecordItem item = new RecordItem();
		Book book = new ProfileService().getBook("0001");
		item.setBook(book);
		item.setCount(10);
		rec.addItem(item);
		
		item = new RecordItem();
		Book book2 = new ProfileService().getBook("0002");
		item.setBook(book2);
		item.setCount(5);
		rec.addItem(item);
		
		new LibraryService().returnBook(borrower, rec);
	}

}
