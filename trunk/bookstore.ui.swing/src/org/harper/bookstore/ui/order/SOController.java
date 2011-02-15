package org.harper.bookstore.ui.order;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang.Validate;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JLabelBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class SOController extends Controller {

	private SOFrame orderFrame;

	private SupplyOrder order;

	private BindingManager manager;

	public SOController(SupplyOrder order) {
		super();

		this.order = order;

		this.orderFrame = new SOFrame(this);
		initLink();
		initManager();
	}

	public SOController() {
		this(new SupplyOrder());
	}

	public SOFrame getOrderFrame() {
		return orderFrame;
	}

	public void setOrderFrame(SOFrame orderFrame) {
		this.orderFrame = orderFrame;
	}

	public SupplyOrder getOrder() {
		return order;
	}

	public void setOrder(SupplyOrder order) {
		this.order = order;
		manager.setBean(order);
	}

	protected void initLink() {
		getOrderFrame().getItemTable().getTableModel()
				.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent e) {
						manager.propertyChange(new PropertyChangeEvent(order,
								"total", null, order.getTotal()));
					}

				});
	}

	protected void initManager() {
		this.manager = new BindingManager(order);
		manager.addBinding(new JComboBinding(getOrderFrame().getHeaderPanel()
				.getSystemCombo(), "supplier.source"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getCustomerIdTextField(), "supplier.id"));
		manager.addBinding(getOrderFrame().getHeaderPanel()
				.getCreateDateTextField().new DateTextBinding("createDate"));
		manager.addBinding(getOrderFrame().getItemTable().getController().new OrderItemTableBinding(
				"items"));
		manager.addBinding(new JComboBinding(getOrderFrame().getHeaderPanel()
				.getSiteCombo(), "site"));
		manager.addBinding(new JLabelBinding(getOrderFrame()
				.getSubtotalAmountLabel(), "total"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getNumberTextField(), "number"));
		manager.addBinding(new JTextBinding(getOrderFrame().getRemarkArea(),
				"remark"));
		manager.loadAll();
	}

	public List<StoreSite> getAvailableSite() {
		return new StoreSiteService().getAvailableSite(false);
	}

	public void save() {
		Validate.notEmpty(order.getSupplier().getId(),
				"Supplier Id should not be empty");
		if (null == order.getSite())
			order.setSite((StoreSite) getOrderFrame().getHeaderPanel()
					.getSiteCombo().getSelectedItem());
		SupplyOrder newOrder = new OrderService().saveSupplyOrder(order);
		setOrder(newOrder);
	}

	public void confirm() {
		setOrder(new OrderService().operateOrder(order,
				SupplyOrder.Status.CONFIRM));
	}

	public void cancel() {
		setOrder(new OrderService().operateOrder(order,
				SupplyOrder.Status.CANCEL));
	}

	public static void main(String[] args) {
		Logger.getLogger("oracle.toplink").addAppender(new ConsoleAppender());
		new SOController();
	}
}
