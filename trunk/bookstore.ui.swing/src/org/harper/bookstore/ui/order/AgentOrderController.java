package org.harper.bookstore.ui.order;

import java.math.BigDecimal;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class AgentOrderController extends Controller {

	private AgentOrderFrame frame;

	private BindingManager manager;

	private AgentOrderBean bean;

	public AgentOrderController() {
		super();
		this.frame = new AgentOrderFrame();
		this.frame.setController(this);

		this.bean = new AgentOrderBean();

		this.manager = new BindingManager(bean);
		initManager();
	}

	protected void initManager() {
		manager.addBinding(new JTextBinding(frame.getCustomerField(),
				"customerId"));
		manager.addBinding(new JTextBinding(frame.getSupplierField(),
				"supplierId"));
		manager.addBinding(new TableBinding(frame.getItemController().getView()
				.getItemTable(), "items"));
		manager.addBinding(new JComboBinding(frame.getCustomerSource(),
				"customerSource"));
		manager.addBinding(new JComboBinding(frame.getSupplierSource(),
				"supplierSource"));
	}

	public void save() {
		new OrderService().placeAgentOrder(bean.getCustomerSource(), bean
				.getCustomerId(), bean.getSupplierSource(), bean
				.getSupplierId(), bean.getItems());
	}

	public static void main(String[] args) {
		new AgentOrderController();
	}
	
	public BigDecimal getListPrice(Book book) {
		return new OrderService().getListPrice(book);
	}

}
