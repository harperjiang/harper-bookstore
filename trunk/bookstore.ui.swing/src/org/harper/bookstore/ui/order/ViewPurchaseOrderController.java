package org.harper.bookstore.ui.order;

import java.util.ArrayList;
import java.util.List;

import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ViewPurchaseOrderController extends Controller {

	private ViewPurchaseOrderFrame frame;

	private BindingManager manager;

	private ViewPurchaseOrderBean bean;

	public ViewPurchaseOrderController(ViewPurchaseOrderBean bean) {
		super();

		this.frame = new ViewPurchaseOrderFrame();
		this.frame.setController(this);

		if (null == bean)
			bean = new ViewPurchaseOrderBean();
		this.bean = bean;
		initManager();
	}

	public ViewPurchaseOrderController() {
		this(null);
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(frame.getOrderNumField(),
				"orderNum"));
		manager.addBinding(new JComboBinding(frame.getDeliveryStatusCombo(),
				"deliveryStatus"));
		manager.addBinding(new JComboBinding(frame.getStatusCombo(), "status"));
		manager.addBinding(frame.getStartDateField().new DateTextBinding(
				"startDate"));
		manager.addBinding(frame.getStopDateField().new DateTextBinding(
				"stopDate"));
		manager.addBinding(new JTextBinding(frame.getPartyIdField(), "partyId"));
		manager.addBinding(new JTextBinding(frame.getPowersearchField(),
				"powersearch"));
		manager.addBinding(new TableBinding(frame.getOrderTable(),
				"searchResults"));

		manager.loadAll();
	}

	public void search() {
		bean.setSearchResults((List) new OrderService().searchOrder(bean
				.getOrderNum(), bean.getOrderType(), bean.getStartDate(), bean
				.getStopDate(), null == bean.getStatus() ? null
				: new int[] { bean.getStatus().ordinal() }, null == bean
				.getDeliveryStatus() ? null : new int[] { bean
				.getDeliveryStatus().ordinal() }, bean.getPartyId(), bean
				.getPowersearch()));
	}

	public void batchDeliver(int[] selected) {
		List<PurchaseOrder> orders = new ArrayList<PurchaseOrder>();
		for (int i : selected) {
			orders.add(getBean().getSearchResults().get(i));
		}
		
	}

	public static void main(String[] args) {
		new ViewPurchaseOrderController(null);
	}

	public ViewPurchaseOrderFrame getFrame() {
		return frame;
	}

	public BindingManager getManager() {
		return manager;
	}

	public ViewPurchaseOrderBean getBean() {
		return bean;
	}

}
