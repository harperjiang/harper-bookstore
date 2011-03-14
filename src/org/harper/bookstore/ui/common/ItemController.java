package org.harper.bookstore.ui.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;
import org.harper.frm.gui.swing.comp.table.data.TableData;

public abstract class ItemController<T extends Item> extends Controller {

	private ItemTable<T> view;

	private Map<String, Integer> itemIndex;

	private Map<String, T> items;

	public ItemController(List<T> model, TableCreator creator) {
		super();
		this.view = new ItemTable<T>(creator);
		this.view.setController(this);

		items = new HashMap<String, T>();
		itemIndex = new HashMap<String, Integer>();
		setData(model);
	}

	protected void setData(List<T> model) {
		if (null == model)
			return;
		int count = 0;
		items.clear();
		itemIndex.clear();

		List<TableData> tableDatas = new ArrayList<TableData>();

		for (T item : model) {
			items.put(item.getBook().getIsbn(), item);
			itemIndex.put(item.getBook().getIsbn(), count++);

			tableDatas.add(createTableData(item));
		}
		// Update Table Data
		view.getTableModel().setData(tableDatas);
	}

	protected List<T> getData() {
		List<T> items = new ArrayList<T>();
		List<AbstractTableData> tableData = (List<AbstractTableData>) view
				.getTableModel().getData();
		for (AbstractTableData oit : tableData)
			items.add((T) oit.getBean());
		return items;
	}

	public void add(Book book) {
		if (book instanceof BookSet) {
			BookSet set = (BookSet) book;
			for (BookUnit item : set.getBooks())
				add(item.getBook());
			return;
		}
		String isbn = book.getIsbn();
		if (items.containsKey(book.getIsbn())) {
			// Updated Row
			Integer index = itemIndex.get(isbn);
			T oldItem = items.get(isbn);
			oldItem.setCount(oldItem.getCount() + 1);
			view.getTableModel().updateRow(index, createTableData(oldItem));
			// Select The Row
			view.getItemTable().getSelectionModel()
					.setSelectionInterval(index, index);
		} else {
			// New Row
			int index = view.getTableModel().getRowCount();
			T newItem = createItem(book);
			newItem.setBook(book);
			newItem.setCount(1);
			items.put(isbn, newItem);
			itemIndex.put(isbn, index);
			view.getTableModel().addRow(createTableData(newItem));
			view.getItemTable().getSelectionModel()
					.setSelectionInterval(index, index);
		}
	}

	public void remove(int index) {
		T item = (T) ((AbstractTableData) view.getTableModel().getData()
				.get(index)).getBean();
		items.remove(item.getBook().getIsbn());
		itemIndex.remove(item.getBook().getIsbn());
		view.getTableModel().removeRow(index);
	}

	public ProfileRepo getProfileRepo() {
		return getRepoFactory().getProfileRepo();
	}

	public ItemTable<T> getView() {
		return view;
	}

	protected abstract T createItem(Book book);

	protected abstract TableData createTableData(Object item);

	public static interface TableCreator {
		public void createTable(JTable table);
	}
}
