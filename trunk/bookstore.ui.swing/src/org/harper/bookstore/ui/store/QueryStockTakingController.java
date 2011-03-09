package org.harper.bookstore.ui.store;

import java.util.List;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;

public class QueryStockTakingController extends Controller {

	QueryStockTakingBean bean;

	QueryStockTakingFrame frame;

	public QueryStockTakingController() {
		super();
		this.frame = new QueryStockTakingFrame();
		this.frame.setController(this);

		this.bean = new QueryStockTakingBean();
		initBean();
		initManager();
	}

	protected void initBean() {
		List<StoreSite> sites = new StoreSiteService().getAvailableSite(false);
		bean.setSite(sites.get(0));

		for (StoreSite site : sites)
			frame.getSiteCombo().addItem(site);
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(frame.getFromDateField().new DateTextBinding("from"));
		manager.addBinding(frame.getToDateField().new DateTextBinding("to"));
		manager.addBinding(new JComboBinding(frame.getSiteCombo(), "site"));
		manager.addBinding(new JComboBinding(frame.getStatusCombo(), "status"));

		manager.addBinding(new TableBinding(frame.getResultTable(), "result"));

		manager.loadAll();
	}

	public void search() {
		bean.setResult(new StoreSiteService().searchStockTakings(
				bean.getFrom(), bean.getTo(), bean.getStatus(), bean.getSite()));
	}
}
