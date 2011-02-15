package org.harper.tool.importbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import oracle.toplink.sessions.UnitOfWork;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.toplink.SessionManager;

import com.oocl.frm.data.ITable;
import com.oocl.frm.data.parser.CSV2TableParser;

public class ImportBook {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CSV2TableParser parser = new CSV2TableParser(true,"gbk");
		parser.setHeaderExists(true);
		ITable table1 = (ITable) parser.parse(new FileInputStream(
				"book_home.csv"));

		ITable table2 = (ITable) parser.parse(new FileInputStream(
				"book_4_12.csv"));

//		ITable table3 = (ITable) parser.parse(new FileInputStream(
//				"newbook_import.csv"));

		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		Map<String, Book> books = new HashMap<String, Book>();

		for (int i = 0; i < table1.getRowCount(); i++) {
			String isbn = (String) table1.getValueAt(i, 3);
			String bookName = (String) table1.getValueAt(i, 0);

			if(StringUtils.isEmpty(isbn))
				continue;
			if (null != isbn)
				isbn = isbn.trim();
			if (books.containsKey(isbn))
				continue;
			Book book = new Book();
			book.setIsbn(isbn);
			book.setName(bookName);
			books.put(isbn, book);
		}
		for (int i = 0; i < table2.getRowCount(); i++) {
			String isbn = (String) table2.getValueAt(i, 1);
			String bookName = (String) table2.getValueAt(i, 0);

			if (null != isbn)
				isbn = isbn.trim();
			if (StringUtils.isEmpty(isbn))
				continue;
			if (books.containsKey(isbn))
				continue;
			Book book = new Book();
			book.setIsbn(isbn);
			book.setName(bookName);
			books.put(isbn, book);
		}
//		for (int i = 0; i < table3.getRowCount(); i++) {
//			String isbn = (String) table3.getValueAt(i, 0);
//			String name = (String) table3.getValueAt(i, 1);
//			if(null!= isbn)
//				isbn = isbn.trim();
//			if (StringUtils.isEmpty(isbn)
//					|| StringUtils.isEmpty(name))
//				continue;
//			if (books.containsKey(isbn))
//				continue;
//			Book book = new Book();
//			book.setIsbn(isbn);
//			book.setName(name.trim());
//			books.put(isbn, book);
//		}

//		for(Book book:books.values())
//			System.out.println(book.getName());
		uow.registerAllObjects(books.values());

		uow.commit();
	}

}
