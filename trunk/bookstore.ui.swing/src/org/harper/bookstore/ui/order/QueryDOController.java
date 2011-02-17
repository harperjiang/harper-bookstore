package org.harper.bookstore.ui.order;

import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class QueryDOController extends Controller {

	private QueryDOFrame frame;

	private QueryDOBean bean;

	public QueryDOController() {
		super();

		frame = new QueryDOFrame();
		frame.setController(this);

		bean = new QueryDOBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getFromDateField().new DateTextBinding(
				"fromDate"));
		manager.addBinding(frame.getToDateField().new DateTextBinding("toDate"));
		manager.addBinding(new JTextBinding(frame.getConsigneeNameField(),
				"consigneeName"));
		manager.addBinding(new JTextBinding(frame.getPoNumberField(),
				"poNumber"));
		manager.addBinding(new JTextBinding(frame.getPoCustomerIdField(),
				"poCustomerId"));
		manager.addBinding(new TableBinding(frame.getQueryDoTable(), "orders"));
		manager.loadAll();
	}

	public void search() {
		bean.setOrders(new OrderService().searchDeliveryOrder(
				bean.getFromDate(), bean.getToDate(), bean.getPoNumber(),
				bean.getConsigneeName(), bean.getPoCustomerId()));
	}

	public static void main(String[] args) {
		new QueryDOController();
	}
}
