package org.harper.bookstore.ui.setting;

import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ContactInfoController extends Controller {

	private ContactInfoFrame frame;

	private ContactInfoBean bean;

	public ContactInfoController() {
		super();

		this.frame = new ContactInfoFrame();
		frame.setController(this);

		bean = new ContactInfoBean();
		bean.load();
		initManager();
	}

	protected void initManager() {
		manager.addBinding(new JTextBinding(frame.getPanel().getNameField(),
				"name"));
		manager.addBinding(new JTextBinding(frame.getPanel().getAddressArea(),
				"address"));
		manager.addBinding(new JTextBinding(frame.getPanel().getMobileField(),
				"mobile"));
		manager.loadAll();
	}
	
	public void save() {
		bean.save();
	}

	public static void main(String[] args) {
		new ContactInfoController();
	}
}
