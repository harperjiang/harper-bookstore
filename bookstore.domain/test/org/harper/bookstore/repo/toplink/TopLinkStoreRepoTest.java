package org.harper.bookstore.repo.toplink;

import org.harper.bookstore.domain.profile.Book;
import org.junit.Test;

public class TopLinkStoreRepoTest {

	@Test
	public void testCheckBookExistance() {
		Book book = new Book();
		book.setIsbn("0001");
		new TopLinkStoreRepo().checkBookExistance(book);
	}

}
