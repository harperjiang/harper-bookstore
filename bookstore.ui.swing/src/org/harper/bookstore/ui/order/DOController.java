package org.harper.bookstore.ui.order;

import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;

public class DOController extends Controller {

	private DOFrame frame;
	
	private BindingManager manager;
	
	private DeliveryOrder bean;
	
	public DOController() {
		super();
		
		frame = new DOFrame();
		frame.setController(this);
		
		bean = new DeliveryOrder();
		initManager();
	}
	
	protected void initManager() {
		manager = new BindingManager(bean);
	}

	public void save() {
		
	}
	
	public static void main(String[] args) {
		new DOController();
	}
}
