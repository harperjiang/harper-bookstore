package org.harper.bookstore.ui.store;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class ManageStoreController extends Controller {

	private ManageStoreFrame frame;

	private BindingManager manager;

	private StoreSiteInfoBean bean;

	public ManageStoreController() {
		super();
		this.frame = new ManageStoreFrame(this);

		bean = new StoreSiteInfoBean();
		createManager();
		load();
	}

	public void load() {
		List<StoreSite> stores = new StoreSiteService().getAllSites();
		List<StoreSite> beans = new ArrayList<StoreSite>();

		beans.addAll(stores);
		getBean().setSites(beans);
	}

	public void save() {
		StoreSiteService ps = new StoreSiteService();
		ps.saveSites(bean.getSites());
	}

	protected void createManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new TableBinding(frame.getStoreTable(), "sites"));
		manager.loadAll();
	}

	public StoreSiteInfoBean getBean() {
		return bean;
	}@Override
	public JComponent getComponent() {
		return frame;
	}
}
