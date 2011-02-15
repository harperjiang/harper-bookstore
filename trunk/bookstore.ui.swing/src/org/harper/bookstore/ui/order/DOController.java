package org.harper.bookstore.ui.order;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class DOController extends Controller {

	private DOFrame frame;

	private BindingManager manager;

	private DeliveryOrderBean bean;

	public DOController() {
		super();

		frame = new DOFrame();
		frame.setController(this);

		bean = new DeliveryOrderBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(new JTextBinding(frame.getPoNumberField(),
				"poNumber"));
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

		manager.addBinding(new TableBinding(frame.getDoItemController()
				.getView().getItemTable(), "delivery.items"));
	}

	public void save(boolean forceClose) {
		Validate.isTrue(!StringUtils.isEmpty(bean.getPoNumber()),
				"Please input a valid PO number");
	}

	public static void main(String[] args) {
		new DOController();
	}
}
