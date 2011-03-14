package org.harper.bookstore.ui.store;

import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.domain.store.StockTakingItem;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class StockTakingController extends Controller {

	private StockTaking bean;

	private StockTakingFrame frame;

	public StockTakingController(StockTaking bean) {
		super();
		this.bean = null == bean ? new StockTaking() : bean;

		this.frame = new StockTakingFrame(this);

		this.bean.setSite((StoreSite) frame.getSiteCombo().getSelectedItem());
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(new JComboBinding(frame.getSiteCombo(), "site"));

		manager.addBinding(frame.getCreateDateField().new DateTextBinding(
				"createDate"));
		manager.addBinding(new JTextBinding(frame.getStatusField(), "statusStr"));
		manager.addBinding(new TableBinding(frame.getItemController().getView()
				.getItemTable(), "items"));
		manager.addBinding(new JTextBinding(frame.getNumberField(), "number"));

		manager.loadAll();
	}

	protected void fillItem(StockTakingItem item) {
		// TODO Fill Item
		StoreEntry entry = bean.getSite().getEntry(item.getBook());
		if (null != entry) {
			item.setOriginCount(entry.getCount());
			item.setUnitPrice(entry.getUnitPrice());
		} else {
			throw new IllegalArgumentException(
					"Stock Taking cannot handle books with no history:"
							+ item.getBook().getName());
		}
	}

	public List<StoreSite> getSites() {
		return new StoreSiteService().getAvailableSite(false);
	}

	public StockTakingController() {
		this(null);
	}

	public void save() {
		StockTaking taking = new StoreSiteService().saveStockTaking(bean);
		manager.setBean(taking);
	}

	public void confirm(Date confirmDate) {
		bean.setConfirmDate(confirmDate);
		StockTaking taking = new StoreSiteService().operateStockTaking(bean,
				StockTaking.Status.CONFIRM.ordinal());

		manager.setBean(taking);
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

	public static void main(String[] args) {
		new StockTakingController();
	}

}
