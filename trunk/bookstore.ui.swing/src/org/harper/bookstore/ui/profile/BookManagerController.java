package org.harper.bookstore.ui.profile;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.service.ProfileService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.core.tools.insert.BinaryInserter;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class BookManagerController extends Controller {

	private BookManageFrame frame;

	private BookInfoBean model;

	public BookManagerController() {
		super();
		this.frame = new BookManageFrame();
		this.frame.controller = this;

		model = new BookInfoBean();
		initManager();
		load();
	}

	protected void initManager() {
		manager = new BindingManager(model);
		manager.addBinding(new TableBinding(frame.bookTable, "books"));
	}

	public BookInfoBean getModel() {
		return model;
	}

	public void setModel(BookInfoBean model) {
		this.model = model;
	}

	public void save() {
		ProfileService ps = new ProfileService();
		OrderService os = new OrderService();
		for (BookBean bean : model.getBooks()) {
			ps.saveBook(bean.getBook());
			if (null != bean.getPrice())
				os.updateListPrice(bean.getBook(), bean.getPrice());
		}
	}

	public void load() {
		new SwingWorker<List<Book>, Object>() {
			@Override
			protected List<Book> doInBackground() throws Exception {
				List<Book> books = new ProfileService().getBooks();
				return books;
			}

			@Override
			protected void done() {
				try {
					List<Book> books = get();
					List<BookBean> beans = new ArrayList<BookBean>();
					OrderService orderServ = new OrderService();
					for (Book book : books) {
						BookBean bean = new BookBean();
						bean.setBook(book);
						bean.setPrice(orderServ.getListPrice(book));
						beans.add(bean);
					}
					model.setBooks(beans);
				} catch (Exception e) {
					LogManager.getInstance().getLogger(getClass())
							.error("Error when loading books", e);
				}
			}
		}.execute();
	}

	public void select(Book book) {
		int index = model.getBooks().indexOf(book);
		if (0 <= index) {
			frame.bookTable.getSelectionModel().setSelectionInterval(index,
					index);
		}
	}

	public void add(Book book) {
		BookBean bean = new BookBean();
		bean.setBook(book);
		bean.setPrice(null);
		ArrayList<BookBean> newBooks = new ArrayList<BookBean>();
		newBooks.addAll(model.getBooks());
		new BinaryInserter().insert(bean, newBooks);
		model.setBooks(newBooks);
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

	public static void main(String[] args) {
		new BookManagerController();
	}
}
