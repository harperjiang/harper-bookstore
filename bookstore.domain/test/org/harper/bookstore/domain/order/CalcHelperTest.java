package org.harper.bookstore.domain.order;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookUnit;
import org.junit.Test;

public class CalcHelperTest {

	@Test
	public void testSplit() {
		Book book1 = new Book();
		book1.setIsbn("0001");
		Book book2 = new Book();
		book2.setIsbn("0002");
		List<BookUnit> bul = new ArrayList<BookUnit>();
		bul.add(new BookUnit(book1,1,BigDecimal.ONE));
		bul.add(new BookUnit(book2,1,BigDecimal.ONE));
		BigDecimal[] result = CalcHelper.split(new BigDecimal(100), bul);
		
		assertEquals(2,result.length);
		assertEquals(50,result[0].intValue());
		assertEquals(50,result[1].intValue());
	}

}
