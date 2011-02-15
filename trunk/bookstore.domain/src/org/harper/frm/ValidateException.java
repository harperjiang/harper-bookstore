package org.harper.frm;

import java.text.MessageFormat;

import org.harper.bookstore.domain.profile.Book;

public class ValidateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5289769955986541867L;

	public ValidateException(String msg) {
		super(msg);
	}

	public static ValidateException noSuchBook(String isbn) {
		return new ValidateException("No Such Book:" + isbn);
	}

	public static ValidateException notEnoughBook(Book book, int avail) {
		return new ValidateException(MessageFormat.format(
				"Not enough book {0}({1}), only {2} available", book.getName(),
				book.getIsbn(), avail));
	}

	public static ValidateException isBookSet(String bookIsbn) {
		return new ValidateException(MessageFormat.format("Book {0} is a set",
				bookIsbn));
	}

	public static ValidateException cancelDraftOrder(Book book, int count) {
		return new ValidateException(MessageFormat.format(
				"Book {0}({1}) had been locked, cancel {2} books in draft.",
				book.getName(), book.getIsbn(), count));
	}
}
