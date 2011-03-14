package org.harper.bookstore.ui.library;

import org.harper.bookstore.ui.Controller;

public class ManageBorrowerController extends Controller {

	private ManageBorrowerFrame frame;

	public ManageBorrowerController() {
		super();

		this.frame = new ManageBorrowerFrame();
		this.frame.setController(this);
	}
	
	public static void main(String[] args) {
		new ManageBorrowerController();
	}
}
