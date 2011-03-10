package org.harper.bookstore.ui.main;

import javax.swing.JFrame;

public class MainWindow2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710860169827411879L;

	public MainWindow2() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setSize(800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainWindow2();
	}
}
