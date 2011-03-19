package org.harper.bookstore.cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.frm.data.ITable;
import org.harper.frm.data.parser.CSV2TableParser;

public class FileBookCache {

	private static FileBookCache instance;

	public synchronized static FileBookCache getInstance() {
		if (null == instance)
			instance = new FileBookCache();
		return instance;
	}

	private boolean loaded;

	private Map<String, Book> books;

	public void update() throws Exception {
		List<Book> books = new ProfileService().getBooks();
		FileOutputStream fos = new FileOutputStream("FileBookCache.csv");
		PrintWriter pw = new PrintWriter(fos);
		pw.println("ISBN,Name");
		for (Book book : books) {
			String name = book.getName();
			name = name.replaceAll("\n", " ");
			name = name.replaceAll("\r", " ");
			if (name.contains(","))
				name = "\"" + name + "\"";
			pw.println(book.getIsbn() + "," + name);
		}
		pw.close();
		fos.close();
	}

	protected void load() {
		books = new HashMap<String, Book>();
		CSV2TableParser parser = new CSV2TableParser(true, "UTF-8");
		try {
			ITable table = (ITable) parser.parse(new FileInputStream(
					"FileBookCache.csv"));
			for (int i = 0; i < table.getRowCount(); i++) {
				String isbn = String.valueOf(table.getValueAt(i, 0));
				String name = String.valueOf(table.getValueAt(i, 1));
				if (!StringUtils.isEmpty(isbn)) {
					Book book = new Book();
					book.setIsbn(isbn);
					book.setName(name);
					books.put(isbn, book);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Book getBook(String isbn) {
		if (!loaded)
			load();
		return books.get(isbn);
	}

	public static void main(String[] args) throws Exception {
		FileBookCache.getInstance().update();
	}
}
