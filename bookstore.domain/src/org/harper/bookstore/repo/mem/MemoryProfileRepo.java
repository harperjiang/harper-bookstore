package org.harper.bookstore.repo.mem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.repo.ProfileRepo;

public class MemoryProfileRepo implements ProfileRepo {
	public MemoryProfileRepo() {
		books = new HashMap<String, Book>();
	}

	private Map<String, Book> books;

	@Override
	public Book findBook(String isbn) {
		if (books.containsKey(isbn))
			return books.get(isbn);

		if (isbn.startsWith("t")) {
			Book book = new Book();
			book.setIsbn(isbn);
			book.setName("Book Name " + isbn);
			books.put(isbn, book);
			return book;
		} else
			return null;
	}

	@Override
	public Customer getCustomer(String source, String customerId) {
		return null;
	}

	@Override
	public Supplier getSupplier(String source, String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findBooks(String isbnOrName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> powerFindBooks(String isbnOrName) {
		// TODO Auto-generated method stub
		return null;
	}

}
