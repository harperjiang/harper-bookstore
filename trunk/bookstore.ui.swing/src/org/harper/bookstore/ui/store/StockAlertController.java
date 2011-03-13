package org.harper.bookstore.ui.store;

import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;

public class StockAlertController extends Controller {

	
	private StockAlertFrame frame;
	
	private StockAlertBean bean;
	
	public StockAlertController() {
		super();
		
		frame = new StockAlertFrame();
		frame.setController(this);
		
		bean = new StockAlertBean();
		initManager();
	}
	
	protected void initManager() {
		manager = new BindingManager(bean);
	}
	
	
	
	public static void main(String[] args) {
		new StockAlertController();
	}
}
