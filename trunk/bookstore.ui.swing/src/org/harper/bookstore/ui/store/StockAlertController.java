package org.harper.bookstore.ui.store;

import java.util.List;

import javax.swing.SwingUtilities;

import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;

public class StockAlertController extends Controller {

	private StockAlertFrame frame;

	private StockAlertBean bean;

	public StockAlertController() {
		super();

		frame = new StockAlertFrame();
		frame.setController(this);

		bean = new StockAlertBean();
		initManager();
		load();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
	}

	public void load() {
		new Thread() {
			public void run() {
				final List<StockAlert> alerts = new StoreSiteService()
						.getAlerts(null, null);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						bean.setAlerts(alerts);
					}
				});
			}
		}.start();
	}

	public static void main(String[] args) {
		new StockAlertController();
	}
}
