package org.harper.bookstore.ui.delivery;

import javax.swing.JComponent;

import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class QueryROController extends Controller {

	private QueryROFrame frame;

	private QueryROBean bean;

	public QueryROController() {
		super();

		frame = new QueryROFrame();
		frame.setController(this);

		bean = new QueryROBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getFromCreateDateField().new DateTextBinding(
				"fromCreateDate"));
		manager.addBinding(frame.getToCreateDateField().new DateTextBinding(
				"toCreateDate"));
		manager.addBinding(new JTextBinding(frame.getSenderNameField(),
				"senderName"));
		manager.addBinding(new JTextBinding(frame.getNumberField(), "number"));
		manager.addBinding(new JComboBinding(frame.getStatusCombo(), "status"));
		manager.addBinding(new TableBinding(frame.getQueryRoTable(), "orders"));
		manager.loadAll();
	}

	public void search() {
		bean.setOrders(new OrderService().searchReceiveOrder(
				bean.getFromCreateDate(), bean.getToCreateDate(),
				bean.getFromReceiveDate(), bean.getToReceiveDate(),
				bean.getSenderName(), bean.getNumber(), bean.getStatus()));
	}

	public QueryROBean getBean() {
		return bean;
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

	public static void main(String[] args) {
		new QueryROController();
	}
}
