package org.harper.bookstore.ui.library;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.profile.Borrower;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.LibraryService;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;
import org.springframework.util.CollectionUtils;

public class BorrowBookController extends Controller {

	private BorrowBookFrame frame;

	private BorrowRecord bean;

	private BindingManager manager;

	public BorrowBookController() {
		this(null);
	}

	public BorrowBookController(BorrowRecord bean) {
		super();
		frame = new BorrowBookFrame(this);

		this.bean = bean == null ? createBean() : bean;

		manager = new BindingManager(this.bean);
		initManager();
	}

	public static void main(String[] args) {
		new BorrowBookController();
	}

	public List<StoreSite> getAvailableSite() {
		return new StoreSiteService().getAvailableSite(true);
	}

	public void save() {
		if (null == bean.getSite())
			bean.setSite((StoreSite) frame.getSiteCombo().getSelectedItem());
		Validate.isTrue(!StringUtils.isEmpty(bean.getBorrower().getName()),
				"Please input borrower name");
		// Validate.isTrue(!StringUtils.isEmpty(bean.getBorrower().getCompany()),
		// "Please input Company name");
		Validate.isTrue(!CollectionUtils.isEmpty(bean.getItems()),
				"Please record books to be borrowed");
		new LibraryService().borrowBook(bean.getBorrower(), bean);
	}

	protected BorrowRecord createBean() {
		BorrowRecord b = new BorrowRecord();
		b.setBorrower(new Borrower());
		b.setAccountDate(new Date());
		return b;
	}

	protected void updateData() {
		manager.loadAll();
	}

	protected void initManager() {
		manager.addBinding(new JTextBinding(frame.getBorrowerNameField(),
				"borrower.name"));
		manager.addBinding(new JComboBinding(frame.getSiteCombo(), "site"));
		manager.addBinding(new TableBinding(frame.getItemController().getView()
				.getItemTable(), "items"));
		manager.addBinding(frame.getAccountDateField().new DateTextBinding(
				"accountDate"));

		manager.loadAll();
	}

	public BorrowBookFrame getFrame() {
		return frame;
	}

	public BorrowRecord getBean() {
		return bean;
	}

	public BindingManager getManager() {
		return manager;
	}

}
