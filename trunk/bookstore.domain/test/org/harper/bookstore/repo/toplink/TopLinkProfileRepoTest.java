package org.harper.bookstore.repo.toplink;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.harper.bookstore.domain.profile.Book;
import org.junit.Test;

public class TopLinkProfileRepoTest {

	@Test
	public void testFindBook() {
		
		Book book = new TopLinkProfileRepo().findBook("9780931548642");
		assertNotNull(book);
	}

	@Test
	public void testGetCustomer() {
		fail("Not yet implemented");
	}

}
