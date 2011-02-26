package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class BookSetManageController extends Controller {

	private BookSetManageFrame frame;

	private BookSetBean bean;

	public BookSetManageController(BookSetBean bean) {
		super();

		frame = new BookSetManageFrame();
		frame.setController(this);

		this.bean = bean;
		initManager();
	}

	public BookSetManageController() {
		this(new BookSetBean());
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(frame.getIsbnTextField(), "isbn"));
		manager.addBinding(new JTextBinding(frame.getNameTextField(), "name"));
		manager.addBinding(new TableBinding(frame.getBookTable(), "books"));
		manager.loadAll();
	}

	public void save() {
		new ProfileService().newBookSet(bean.getSet());

		return;
	}

	public void add(Book book) {
		if (bean.getBooks().contains(book))
			return;
		CommonTableModel ctm = (CommonTableModel) frame.getBookTable()
				.getModel();
		BookSetItem item = new BookSetItem();
		item.setBook(book);
		item.setPercentage(BigDecimal.ONE);
		ctm.addRow(new BookSetItemTableData(book));
	}

	public void remove(int index) {
		((CommonTableModel) frame.getBookTable().getModel()).removeRow(index);
	}

	public static void main(String[] args) {
		new BookSetManageController();
	}
}
