package org.harper.bookstore.ui.store;

import java.util.List;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class TransferPlanController extends Controller {

	private TransferPlanFrame frame;

	private BindingManager manager;

	private Transfer bean;

	public TransferPlanController(Transfer bean) {
		if (bean == null) {
			this.bean = new Transfer();
			initBean();
		} else
			this.bean = bean;

		this.frame = new TransferPlanFrame();
		this.frame.setController(this);
		initFrame();
		this.manager = new BindingManager(this.bean);
		initManager();
	}

	public TransferPlanController() {
		this(null);
	}

	protected void initBean() {
		List<StoreSite> sites = new StoreSiteService().getAvailableSite(false);
		bean.setFromSite(sites.get(0));
		bean.setToSite(sites.get(0));
	}

	protected void initFrame() {
		List<StoreSite> sites = new StoreSiteService().getAvailableSite(false);
		for (StoreSite site : sites) {
			frame.getFromSiteCombo().addItem(site);
			frame.getToSiteCombo().addItem(site);
		}
	}

	protected void initManager() {
		manager.addBinding(new JComboBinding(frame.getFromSiteCombo(),
				"fromSite"));
		manager.addBinding(new JComboBinding(frame.getToSiteCombo(), "toSite"));
		manager.addBinding(new TableBinding(frame.getTransferTable()
				.getItemTable(), "items"));
		manager.addBinding(new JTextBinding(frame.getStatusField(), "status"));

		manager.loadAll();
	}

	public void save() {
		setBean(new StoreSiteService().placeTransfer(bean));
	}

	public void confirm() {
		setBean(new StoreSiteService().operateTransfer(bean,
				Transfer.Status.CONFIRM));
	}

	public void cancel() {
		setBean(new StoreSiteService().operateTransfer(bean,
				Transfer.Status.CANCEL));
	}

	public TransferPlanFrame getFrame() {
		return frame;
	}

	public BindingManager getManager() {
		return manager;
	}

	public Transfer getBean() {
		return bean;
	}

	public void setBean(Transfer bean) {
		this.bean = bean;
		manager.setBean(bean);
	}

	public static void main(String[] args) {
		new TransferPlanController();
	}
}
