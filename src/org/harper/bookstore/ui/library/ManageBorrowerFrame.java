package org.harper.bookstore.ui.library;

import javax.swing.JFrame;

public class ManageBorrowerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5421821652066417374L;

	private ManageBorrowerController controller;

	public ManageBorrowerFrame() {
		super();
		setTitle("Manage Borrower");
		setSize(800,600);
		
		setVisible(true);
	}

	public ManageBorrowerController getController() {
		return controller;
	}

	public void setController(ManageBorrowerController controller) {
		this.controller = controller;
	}

}
