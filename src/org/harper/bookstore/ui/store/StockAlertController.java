package org.harper.bookstore.ui.store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.SwingUtilities;

import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.core.tools.insert.BinaryInserter;
import org.harper.frm.gui.MiscUtils;
import org.harper.frm.gui.swing.comp.table.TableBinding;
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

		manager.addBinding(new TableBinding(frame.getAlertTable(), "alerts"));

		manager.loadAll();
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

	public void addAlert(final StockAlert newAlert) {
		// Check existence
		for (StockAlert alert : bean.getAlerts()) {
			if (MiscUtils.equal(alert.getBook(), newAlert.getBook())
					&& MiscUtils.equal(alert.getSite(), newAlert.getSite())
					&& alert.getOid() != newAlert.getOid()) {
				throw new IllegalArgumentException("Already Exist");
			}
		}

		new Thread() {
			public void run() {
				final StockAlert alert = getStoreSiteService().saveAlert(
						newAlert);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						updateList(alert);
					}
				});
			}
		}.start();
	}

	protected void updateList(StockAlert alert) {
		List<StockAlert> newList = new ArrayList<StockAlert>();
		newList.addAll(bean.getAlerts());
		if (!newList.contains(alert)) {
			new BinaryInserter().insert(alert, newList, true,
					new Comparator<StockAlert>() {
						@Override
						public int compare(StockAlert o1, StockAlert o2) {
							int book = o1.getBook().getIsbn()
									.compareTo(o2.getBook().getIsbn());
							if (book != 0)
								return book;

							if (o1.getSite() == o2.getSite())
								return 0;
							if (null == o1.getSite())
								return -1;
							return o1.getSite().getName()
									.compareTo(o2.getSite().getName());
						}
					});
		}
		bean.setAlerts(newList);
	}

	private StoreSiteService storeSiteService;

	public StoreSiteService getStoreSiteService() {
		if (null == storeSiteService)
			storeSiteService = new StoreSiteService();
		return storeSiteService;
	}

	public void setStoreSiteService(StoreSiteService storeSiteService) {
		this.storeSiteService = storeSiteService;
	}

	public StockAlertFrame getFrame() {
		return frame;
	}

	public StockAlertBean getBean() {
		return bean;
	}

}
