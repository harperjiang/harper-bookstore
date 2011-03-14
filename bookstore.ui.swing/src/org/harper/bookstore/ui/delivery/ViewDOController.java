package org.harper.bookstore.ui.delivery;

import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.deliver.DeliveryItem;
import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ViewDOController extends Controller {

	private ViewDOFrame frame;

	private DeliveryOrderBean bean;

	public ViewDOController() {
		super();

		frame = new ViewDOFrame();
		frame.setController(this);

		bean = new DeliveryOrderBean();
		initManager();
	}

	public ViewDOController(DeliveryOrder dobean) {
		this();
		this.bean.setDelivery(dobean);
		manager.loadAll();
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(new JComboBinding(
				frame.getPanel().getCompanyCombo(), "delivery.company"));
		manager.addBinding(new JTextBinding(frame.getPanel()
				.getOrderNumberField(), "delivery.number"));
		manager.addBinding(new JTextBinding(frame.getPanel().getNameField(),
				"delivery.contact.name"));
		manager.addBinding(new JTextBinding(frame.getPanel().getAddressArea(),
				"delivery.contact.address"));
		manager.addBinding(new JTextBinding(frame.getPanel().getEmailField(),
				"delivery.contact.email"));
		manager.addBinding(new JTextBinding(frame.getPanel().getPhoneField(),
				"delivery.contact.phone"));
		manager.addBinding(new JTextBinding(frame.getPanel().getMobileField(),
				"delivery.contact.mobile"));

		manager.addBinding(new TableBinding(frame.getDoItemTable(),
				"delivery.items"));
	}

	public void loadPo() {
		List<Order> pos = new OrderService().searchOrder(bean.getPoNumber(),
				"PO", null, null, new int[] { Order.Status.CONFIRM.ordinal() },
				new int[] {
						PurchaseOrder.DeliveryStatus.PARTIAL_SENT.ordinal(),
						PurchaseOrder.DeliveryStatus.NOT_SENT.ordinal() },
				null, null);
		if (pos.size() > 0) {
			PurchaseOrder po = (PurchaseOrder) pos.get(0);
			// Check whether this PO had been loaded before
			for (DeliveryItem item : bean.getDelivery().getItems()) {
				if (null != item.getOrderItem()
						&& item.getOrderItem().getOrder().getOid() == po
								.getOid())
					return;
			}
			for (OrderItem oi : po.getItems()) {
				DeliveryItem newdi = new DeliveryItem();
				newdi.setHeader(bean.getDelivery());
				newdi.setOrderItem(oi);
				bean.getDelivery().addItem(newdi);
			}
			manager.loadAll();
		} else
			throw new IllegalArgumentException("No Purchase Order Found");
	}

	public void save(boolean forceClose) {
		Validate.isTrue(!StringUtils.isEmpty(bean.getDelivery().getNumber()),
				"Please input a valid Delivery Order number");
		new OrderService().saveDeliveryOrder(bean.getDelivery());
	}

	public void deliver() {
		new OrderService().operateDelivery(bean.getDelivery(),
				DeliveryOrder.Status.DELIVERED.ordinal());
	}

	public void fallback() {
		new OrderService().operateDelivery(bean.getDelivery(),
				DeliveryOrder.Status.RETURNED.ordinal());
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

	public static void main(String[] args) {
		new ViewDOController();
	}
}
