package org.harper.bookstore.ui.store;

import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class ViewTransferController extends Controller {

	private ViewTransferFrame frame;

	private ViewTransferBean bean;

	private BindingManager manager;

	public ViewTransferController() {

		this.frame = new ViewTransferFrame();
		this.frame.setController(this);

		this.bean = new ViewTransferBean();
		this.manager = new BindingManager(bean);
		initManager();
	}

	public void initManager() {
		manager.addBinding(new TableBinding(frame.getTransferTable(), "items"));
	}

	public void search() {
		bean.setItems(new StoreSiteService().searchTransfers(bean.getStatus(),
				bean.getStartDate(), bean.getStopDate(), bean.getFromSite(),
				bean.getToSite()));
	}

	public static void main(String[] args) {
		new ViewTransferController();
	}
}
