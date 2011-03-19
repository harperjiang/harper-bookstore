package org.harper.bookstore.ui.posmode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.core.tools.insert.BinaryInserter;
import org.harper.frm.core.tools.sort.MultiAttrComparator;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class POSModeBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6574209772188952500L;

	private String tradeId = "";

	private List<POSBookItem> items;

	private Map<Book, POSBookItem> itemIndex;

	public POSModeBean() {
		items = new ArrayList<POSBookItem>();
		itemIndex = new HashMap<Book, POSBookItem>();
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		String old = getTradeId();
		this.tradeId = tradeId;
		firePropertyChange("tradeId", old, tradeId);
	}

	public List<POSBookItem> getItems() {
		return items;
	}

	public void setItems(List<POSBookItem> items) {
		List<POSBookItem> oldItems = new ArrayList<POSBookItem>();
		this.items = items;
		firePropertyChange("items", oldItems, items);
	}

	public int addItem(POSBookItem newItem) {
		List<POSBookItem> oldItems = getItems();
		ArrayList<POSBookItem> newItems = new ArrayList<POSBookItem>();
		newItems.addAll(oldItems);
		if (itemIndex.containsKey(newItem.getBook())) {
			POSBookItem currentItem = itemIndex.get(newItem.getBook());
			currentItem.setCount(currentItem.getCount() + 1);
		} else {
			new BinaryInserter().insert(newItem, newItems, true,
					new MultiAttrComparator<POSBookItem>(
							new String[] { "book" }, new boolean[] { true }));
			itemIndex.put(newItem.getBook(), newItem);
		}
		setItems(newItems);
		return newItems.indexOf(newItem);
	}
}
