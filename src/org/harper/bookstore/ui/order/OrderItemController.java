package org.harper.bookstore.ui.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.core.tools.bean.BeanAccess;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.manager.ComponentBinding;


public class OrderItemController extends Controller {

	private OrderItemTable view;

	private Map<String, Integer> itemIndex;

	private Map<String, OrderItem> items;

	public OrderItemController(OrderItemTable view, List<OrderItem> model) {
		super();
		this.view = view;

		items = new HashMap<String, OrderItem>();
		itemIndex = new HashMap<String, Integer>();
		setData(model);
	}

	protected void setData(List<OrderItem> model) {
		if (null == model)
			return;
		int count = 0;
		items.clear();
		itemIndex.clear();

		List<TableData> tableDatas = new ArrayList<TableData>();

		for (OrderItem item : model) {
			items.put(item.getBook().getIsbn(), item);
			itemIndex.put(item.getBook().getIsbn(), count++);

			tableDatas.add(new OrderItemTableData(item));
		}
		// Update Table Data
		view.getTableModel().setData(tableDatas);
	}

	protected List<OrderItem> getData() {
		List<OrderItem> items = new ArrayList<OrderItem>();
		List<OrderItemTableData> tableData = (List<OrderItemTableData>) view
				.getTableModel().getData();
		for (OrderItemTableData oit : tableData)
			items.add((OrderItem) oit.getBean());
		return items;
	}

	public void add(Book book) {
		String isbn = book.getIsbn();
		if (items.containsKey(book.getIsbn())) {
			// Updated Row
			Integer index = itemIndex.get(isbn);
			OrderItem oldItem = items.get(isbn);
			oldItem.setCount(oldItem.getCount() + 1);
			view.postProcessItem(oldItem, false);
			view.getTableModel().updateRow(index,
					new OrderItemTableData(oldItem));
			// Select The Row
			view.getItemTable().getSelectionModel().setSelectionInterval(index,
					index);
		} else {
			// New Row
			int index = view.getTableModel().getRowCount();
			OrderItem newItem = new OrderItem();
			newItem.setBook(book);
			newItem.setCount(1);
			view.postProcessItem(newItem, true);
			items.put(isbn, newItem);
			itemIndex.put(isbn, index);
			view.getTableModel().addRow(new OrderItemTableData(newItem));
			view.getItemTable().getSelectionModel().setSelectionInterval(index,
					index);
		}
	}

	public void remove(int index) {
		OrderItem item = (OrderItem) ((OrderItemTableData) view.getTableModel()
				.getData().get(index)).getBean();
		items.remove(item.getBook().getIsbn());
		itemIndex.remove(item.getBook().getIsbn());
		view.getTableModel().removeRow(index);
	}

	public ProfileRepo getProfileRepo() {
		return getRepoFactory().getProfileRepo();
	}

	public class OrderItemTableBinding extends ComponentBinding {

		public OrderItemTableBinding(String attribute) {
			super();
			setComponent(view.getItemTable());
			setAttribute(attribute);
		}

		private TableModelListener listener = new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				Object oldValue = value;
				CommonTableModel model = (CommonTableModel) ((JTable) getComponent())
						.getModel();
				Object newValue = BeanAccess.getInstance().extract(
						model.getData(), "bean");
				firePropertyChange(oldValue, newValue);
			}
		};

		@Override
		public void setComponent(JComponent component) {
			// Add listener
			super.setComponent(component);
			if (!(component instanceof JTable))
				throw new IllegalArgumentException();
			((JTable) component).getModel().addTableModelListener(listener);
		}

		@Override
		public void setValue(Object value) {
			super.setValue(value);
			setData((List<OrderItem>) value);
		}
	}
}
