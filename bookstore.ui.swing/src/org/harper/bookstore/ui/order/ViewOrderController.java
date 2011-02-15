package org.harper.bookstore.ui.order;

import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ViewOrderController extends Controller {

	private ViewOrderFrame frame;

	private BindingManager manager;

	private ViewOrderBean bean;

	public ViewOrderController(ViewOrderBean bean) {
		super();

		this.frame = new ViewOrderFrame();
		this.frame.setController(this);

		if (null == bean)
			bean = new ViewOrderBean();
		this.bean = bean;
		initManager();
	}

	public ViewOrderController() {
		this(null);
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(frame.getOrderNumField(),"orderNum"));
		manager.addBinding(new JComboBinding(frame.getOrderTypeCombo(),
				"orderType"));
		manager.addBinding(new JComboBinding(frame.getStatusCombo(), "status"));
		manager.addBinding(frame.getStartDateField().new DateTextBinding(
				"startDate"));
		manager.addBinding(frame.getStopDateField().new DateTextBinding(
				"stopDate"));
		manager
				.addBinding(new JTextBinding(frame.getPartyIdField(), "partyId"));
		manager.addBinding(new TableBinding(frame.getOrderTable(),
				"searchResults"));

		manager.loadAll();
	}

	public void search() {
		bean.setSearchResults(new OrderService().searchOrder(
				bean.getOrderNum(), bean.getOrderType(), bean.getStartDate(),
				bean.getStopDate(), bean.getStatus(), bean.getPartyId()));
	}

	public static void main(String[] args) {
		new ViewOrderController(null);
	}

	public ViewOrderFrame getFrame() {
		return frame;
	}

	public BindingManager getManager() {
		return manager;
	}

	public ViewOrderBean getBean() {
		return bean;
	}
}
