package org.harper.bookstore.ui.library;

import org.harper.bookstore.service.LibraryService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class FindBorrowerController extends Controller {

	private FindBorrowerDialog dialog;

	private FindBorrowerBean bean;

	private BindingManager manager;

	public FindBorrowerController(FindBorrowerDialog dialog) {
		super();
		this.bean = new FindBorrowerBean();
		this.manager = new BindingManager(bean);
		this.dialog = dialog;
		this.dialog.setController(this);
		initManager();
		this.dialog.setVisible(true);
	}

	protected void initManager() {
		manager.addBinding(new JTextBinding(dialog.getNameField(), "name"));
		manager
				.addBinding(new JTextBinding(dialog.getCompanyField(),
						"company"));
		manager.addBinding(new TableBinding(dialog.getBorrowerTable(),
				"borrowers"));
	}

	public void search() {
		bean.setBorrowers(new LibraryService().findBorrowers(bean.getName(),
				bean.getCompany()));
	}

	public FindBorrowerDialog getDialog() {
		return dialog;
	}

	public FindBorrowerBean getBean() {
		return bean;
	}
}
