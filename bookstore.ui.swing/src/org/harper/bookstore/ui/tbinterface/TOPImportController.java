package org.harper.bookstore.ui.tbinterface;

import org.harper.bookstore.service.InterfaceService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;

public class TOPImportController extends Controller {

	TOPImportFrame frame;

	TOPImportBean bean;

	public TOPImportController() {
		super();

		frame = new TOPImportFrame();
		frame.setController(this);

		bean = new TOPImportBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getStartDateField().new DateTextBinding(
				"startDate"));
		manager.addBinding(frame.getStopDateField().new DateTextBinding(
				"stopDate"));
		manager.loadAll();
	}

	public int execute() {
		return new InterfaceService().importTOPOrder(bean.getStartDate(),
				bean.getStopDate());
	}

	public static void main(String[] args) {
		new TOPImportController();
	}
}
