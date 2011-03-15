package org.harper.bookstore.ui.delivery;

import java.util.Date;

import javax.swing.JComponent;

import org.harper.bookstore.cache.Cache;
import org.harper.bookstore.domain.deliver.ReceiveOrder;
import org.harper.bookstore.domain.deliver.ReceiveOrder.ReceiveType;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ROController extends Controller {

	private ROFrame frame;

	private ReceiveOrder bean;

	public ROController() {
		this(null);
	}

	public ROController(ReceiveOrder b) {
		super();
		frame = new ROFrame();
		frame.setController(this);
		bean = null == b ? new ReceiveOrder() {
			{
				setCreateDate(new Date());
				setSite(Cache.getInstance().getValidSites().get(0));
				setType(ReceiveType.RETURN);
			}
		} : b;

		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getCreateDateField().new DateTextBinding(
				"createDate"));
		manager.addBinding(new JComboBinding(frame.getTypeCombo(), "type"));
		manager.addBinding(new JComboBinding(frame.getSiteCombo(), "site"));
		manager.addBinding(new TableBinding(frame.getRoItemController()
				.getView().getItemTable(), "items"));
		manager.addBinding(new JTextBinding(frame.getRemarkArea(), "remark"));
		manager.addBinding(new JComboBinding(
				frame.getPanel().getCompanyCombo(), "company"));
		manager.addBinding(new JTextBinding(frame.getPanel()
				.getOrderNumberField(), "number"));
		manager.addBinding(new JTextBinding(frame.getPanel().getNameField(),
				"sender.name"));
		manager.addBinding(new JTextBinding(frame.getPanel().getEmailField(),
				"sender.email"));
		manager.addBinding(new JTextBinding(frame.getPanel().getPhoneField(),
				"sender.phone"));
		manager.addBinding(new JTextBinding(frame.getPanel().getMobileField(),
				"sender.mobile"));

		manager.loadAll();
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}
}
