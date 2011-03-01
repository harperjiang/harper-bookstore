package org.harper.tool.import2;

import java.io.FileInputStream;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.frm.data.ITable;
import org.harper.frm.data.parser.CSV2TableParser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CSV2TableParser parser = new CSV2TableParser(true);
		parser.setCharSet("gb2312");
		ITable table = (ITable) parser.parse(new FileInputStream(
				"D:/仓库书架数据2.26.csv"));

		ProfileService ps = new ProfileService();
		for (int i = 0; i < table.getRowCount(); i++) {
			String isbn = (String) table.getValueAt(i, 0);
			if (null != isbn)
				isbn = isbn.trim();
			String name = (String) table.getValueAt(i, 1);
			if (null != name)
				name = name.trim();
			String page = (String) table.getValueAt(i, 2);
			if (null != page)
				page = page.trim();
			String weight = (String) table.getValueAt(i, 3);
			if (null != weight)
				weight = weight.trim();

			Book book = ps.getBook(isbn);
			if (null == book) {
				book = new Book();
				book.setIsbn(StringUtils.isEmpty(isbn) ? name.toUpperCase()
						.substring(0, 19) : isbn);
				book.setName(name);
				book.setPage(StringUtils.isEmpty(page) ? 0 : Integer
						.valueOf(page));
				try {
					book.setWeight(StringUtils.isEmpty(weight) ? null
							: new BigDecimal(weight));
				} catch (NumberFormatException e) {
					System.out.println(book.getIsbn());
				}
				ps.newBook(book);
			}
		}
	}
}
