package org.harper.bookstore.domain.order;

import static org.junit.Assert.assertEquals;

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
	
	@Test
	public void testSplit2() {
		Book book1 = new Book();
		book1.setIsbn("0001");
		Book book2 = new Book();
		book2.setIsbn("0002");
		Book book3 = new Book();
		book2.setIsbn("0003");
		List<BookUnit> bul = new ArrayList<BookUnit>();
		bul.add(new BookUnit(book1,1,BigDecimal.ONE));
		bul.add(new BookUnit(book2,1,BigDecimal.ONE));
		bul.add(new BookUnit(book3,1,BigDecimal.ONE));
		BigDecimal[] result = CalcHelper.split(new BigDecimal(299), bul);
		
		assertEquals(3,result.length);
		assertEquals(new BigDecimal("99.67"),result[0]);
		assertEquals(new BigDecimal("99.67"),result[1]);
		assertEquals(new BigDecimal("99.66"),result[2]);
	}

}
