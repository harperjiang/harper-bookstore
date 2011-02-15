package org.harper.bookstore.ui.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.TransferItem;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.data.TableData;

public class TransferItemController extends Controller {

	private TransferItemTable view;

	private Map<String, Integer> itemIndex;

	private Map<String, TransferItem> items;

	public TransferItemController(TransferItemTable view, List<TransferItem> model) {
		super();
		this.view = view;

		items = new HashMap<String, TransferItem>();
		itemIndex = new HashMap<String, Integer>();
		setData(model);
	}

	protected void setData(List<TransferItem> model) {
		if (null == model)
			return;
		int count = 0;
		items.clear();
		itemIndex.clear();

		List<TableData> tableDatas = new ArrayList<TableData>();

		for (TransferItem item : model) {
			items.put(item.getBook().getIsbn(), item);
			itemIndex.put(item.getBook().getIsbn(), count++);

			tableDatas.add(new TransferItemTableData(item));
		}
		// Update Table Data
		view.getTableModel().setData(tableDatas);
	}

	protected List<TransferItem> getData() {
		List<TransferItem> items = new ArrayList<TransferItem>();
		List<TransferItemTableData> tableData = (List<TransferItemTableData>) view
				.getTableModel().getData();
		for (TransferItemTableData oit : tableData)
			items.add((TransferItem) oit.getBean());
		return items;
	}

	public void add(Book book) {
		String isbn = book.getIsbn();
		if (items.containsKey(book.getIsbn())) {
			// Updated Row
			Integer index = itemIndex.get(isbn);
			TransferItem oldItem = items.get(isbn);
			oldItem.setCount(oldItem.getCount() + 1);
			view.getTableModel().updateRow(index,
					new TransferItemTableData(oldItem));
			// Select The Row
			view.getItemTable().getSelectionModel().setSelectionInterval(index,
					index);
		} else {
			// New Row
			int index = view.getTableModel().getRowCount();
			TransferItem newItem = new TransferItem();
			newItem.setBook(book);
			newItem.setCount(1);
			items.put(isbn, newItem);
			itemIndex.put(isbn, index);
			view.getTableModel().addRow(new TransferItemTableData(newItem));
			view.getItemTable().getSelectionModel().setSelectionInterval(index,
					index);
		}
	}

	public void remove(int index) {
		TransferItem item = (TransferItem) ((TransferItemTableData) view.getTableModel()
				.getData().get(index)).getBean();
		items.remove(item.getBook().getIsbn());
		itemIndex.remove(item.getBook().getIsbn());
		view.getTableModel().removeRow(index);
	}

	public ProfileRepo getProfileRepo() {
		return getRepoFactory().getProfileRepo();
	}
}
