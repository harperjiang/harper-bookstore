package org.harper.tool.bookcount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ListSelectionModel;

import org.harper.frm.data.ITable;
import org.harper.frm.data.parser.CSV2TableParser;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.AbstractEditableTableData;
import org.harper.frm.gui.swing.comp.table.data.TableData;

public class BookCounter {

	private Map<String, Integer> itemIndex;

	private Map<String, BookCountItem> items;

	private CommonTableModel tableModel;

	private ListSelectionModel selModel;

	private Properties prop;

	public static final String LAST_DIR = "last.dir";

	public BookCounter(CommonTableModel model, ListSelectionModel selModel) {
		super();
		items = new HashMap<String, BookCountItem>();
		itemIndex = new HashMap<String, Integer>();
		tableModel = model;
		this.selModel = selModel;
		prop = new Properties();
		try {
			prop.load(BookCounter.class
					.getResourceAsStream("bookcount.properties"));
		} catch (Exception e) {

		}
	}

	public void add(String isbn) {
		if (null == isbn || isbn.length() == 0)
			return;
		isbn = isbn.replaceAll("\n", "");
		if (items.containsKey(isbn)) {
			// Updated Row
			Integer index = itemIndex.get(isbn);
			BookCountItem oldItem = items.get(isbn);
			oldItem.setCount(oldItem.getCount() + 1);
			tableModel.getData().set(index, new BookCountTableData(oldItem));
			tableModel.fireTableRowsUpdated(index, index);
			selModel.setSelectionInterval(index, index);
		} else {
			// New Row
			int index = tableModel.getRowCount();
			BookCountItem newItem = new BookCountItem();
			newItem.setIsbn(isbn);
			newItem.setCount(1);
			items.put(isbn, newItem);
			itemIndex.put(isbn, index);
			tableModel.addRow(new BookCountTableData(newItem));
			selModel.setSelectionInterval(index, index);
		}
	}

	public void remove(int index) {
		BookCountItem item = (BookCountItem) ((BookCountTableData) tableModel
				.getData().get(index)).getBean();
		items.remove(item.getIsbn());
		itemIndex.remove(item.getIsbn());
		tableModel.removeRow(index);
	}

	public void export(String fileName) throws IOException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

		pw.println("ISBN,Count,Book Name");
		for (BookCountTableData data : (List<BookCountTableData>) tableModel
				.getData()) {
			BookCountItem item = (BookCountItem) data.getBean();
			pw.print(item.getIsbn());
			pw.print(",");
			pw.print(item.getCount());
			pw.print(",");
			pw.println(item.getName());
		}

		pw.close();
	}

	public void importData(String fileName) throws Exception {
		ITable table = (ITable) new CSV2TableParser(true)
				.parse(new FileInputStream(fileName));
		List<TableData> datas = new ArrayList<TableData>();
		for (int i = 0; i < table.getRowCount(); i++) {
			BookCountItem item = new BookCountItem();
			item.setIsbn(String.valueOf(table.getValueAt(i, 0)));
			item.setCount(Integer.parseInt(String.valueOf(table
					.getValueAt(i, 1))));
			item.setName(String.valueOf(table.getValueAt(i, 2)));

			items.put(item.getIsbn(), item);
			itemIndex.put(item.getIsbn(), i);

			datas.add(new BookCountTableData(item));
		}
		tableModel.setData(datas);

	}

	public static class BookCountTableData extends AbstractEditableTableData {
		public BookCountTableData() {
			super();
			descBeans
					.add(new ColumnDescBean(String.class, "ISBN", "isbn"));
			descBeans.add(new ColumnDescBean(Integer.TYPE, "Count",
					"count"));
			descBeans.add(new ColumnDescBean(String.class, "Book Name",
					"name"));
		}

		public BookCountTableData(BookCountItem item) {
			this();
			setBean(item);
		}
	}

	public Properties getProp() {
		return prop;
	}

	public String getLastDir() {
		return getProp().getProperty(LAST_DIR);
	}

	public void setLastDir(String dir) {
		getProp().setProperty(LAST_DIR, dir);
	}

	protected void savePreference() {
		try {
			prop.store(new FileOutputStream("bookstore.properties"), String
					.valueOf(new Date()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
