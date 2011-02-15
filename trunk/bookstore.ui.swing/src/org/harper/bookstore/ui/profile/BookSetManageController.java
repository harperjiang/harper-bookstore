package org.harper.bookstore.ui.profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.domain.profile.BookUnit;
import org.harper.bookstore.service.ProfileService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class BookSetManageController extends Controller {

	private BookSetManageFrame frame;

	private BookSetBean bean;

	public BookSetManageController() {
		super();

		frame = new BookSetManageFrame();
		frame.setController(this);

		bean = new BookSetBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(frame.getIsbnTextField(), "isbn"));
		manager.addBinding(new JTextBinding(frame.getNameTextField(), "name"));
		manager.addBinding(new TableBinding(frame.getBookTable(), "books"));
	}

	public void save() {
		BookSet set = new BookSet();
		set.setIsbn(bean.getIsbn());
		set.setName(bean.getName());
		List<BookUnit> bul = new ArrayList<BookUnit>();
		for (Book book : bean.getBooks()) {
			bul.add(new BookUnit(book, 1, BigDecimal.ONE));
		}
		set.setBooks(bul);

		new ProfileService().newBookSet(set);

		return;
	}

	public void add(Book book) {
		if (bean.getBooks().contains(book))
			return;
		CommonTableModel ctm = (CommonTableModel) frame.getBookTable()
				.getModel();
		ctm.addRow(new BookInfoTableData(book));
	}

	public void remove(int index) {
		((CommonTableModel) frame.getBookTable().getModel()).removeRow(index);
	}

	public static void main(String[] args) {
		new BookSetManageController();
	}
}
