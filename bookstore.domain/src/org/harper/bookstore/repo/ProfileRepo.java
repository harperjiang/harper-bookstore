package org.harper.bookstore.repo;

import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.domain.user.User;

public interface ProfileRepo {

	public Book findBook(String isbn);
	
	public List<Book> findBooks(String isbnOrName);

	public Customer getCustomer(String source, String customerId);

	public Supplier getSupplier(String source, String customerId);

	public List<Book> getBooks();

	public List<Book> powerFindBooks(String isbnOrName);

	public User getUser(String id);

}
