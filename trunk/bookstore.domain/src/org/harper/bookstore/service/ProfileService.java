package org.harper.bookstore.service;

import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.repo.RepoFactory;

public class ProfileService extends Service {

	public List<Book> findBooks(String isbnOrName) {
		startTransaction();
		try {
			return RepoFactory.INSTANCE.getProfileRepo().findBooks(isbnOrName);
		} finally {
			releaseTransaction();
		}
	}

	public List<Book> powerFindBooks(String isbnOrName) {
		startTransaction();
		try {
			return RepoFactory.INSTANCE.getProfileRepo().powerFindBooks(
					isbnOrName);
		} finally {
			releaseTransaction();
		}
	}

	public Book getBook(String isbn) {
		startTransaction();
		try {
			return RepoFactory.INSTANCE.getProfileRepo().findBook(isbn);
		} finally {
			releaseTransaction();
		}
	}

	public List<Book> getBooks() {
		return RepoFactory.INSTANCE.getProfileRepo().getBooks();
	}

	public Book newBook(Book newBook) {
		startTransaction();
		Book book = RepoFactory.INSTANCE.getCommonRepo().store(newBook);
		commitTransaction();
		return book;
	}

	public BookSet newBookSet(BookSet newSet) {
		startTransaction();
		BookSet book = RepoFactory.INSTANCE.getCommonRepo().store(newSet);
		commitTransaction();
		return book;
	}

	public void saveBook(Book book) {
		startTransaction();
		RepoFactory.INSTANCE.getCommonRepo().store(book);
		commitTransaction();
	}

	public void saveBooks(List<Book> books) {
		startTransaction();
		RepoFactory.INSTANCE.getCommonRepo().store(books);
		commitTransaction();
	}

	public StoreSite newStoreSite(StoreSite newStore) {
		startTransaction();
		StoreSite storeSite = RepoFactory.INSTANCE.getCommonRepo().store(
				newStore);
		commitTransaction();
		return storeSite;
	}
}
