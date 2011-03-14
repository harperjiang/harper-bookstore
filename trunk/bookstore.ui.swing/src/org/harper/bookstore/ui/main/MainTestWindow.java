package org.harper.bookstore.ui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.harper.bookstore.ui.order.POController;

public class MainTestWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710860169827411879L;

	public MainTestWindow() {
		super();
		setTitle(Messages.getString("MainWindow.title")); //$NON-NLS-1$
//		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setPreferredSize(new Dimension(800, 600));
		setSize(800,650);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JDesktopPane desktopPane = new JDesktopPane();
		add(desktopPane,BorderLayout.CENTER);

		JInternalFrame internalFrame = new JInternalFrame("Test Window", true,
				true, true, true);
		internalFrame.setSize(new Dimension(200,200));
		internalFrame.setLayout(new FlowLayout());
		JComboBox combo = new JComboBox();
		combo.addItem("001");
		combo.addItem("002");
		combo.addItem("003");
		internalFrame.add(combo);
		desktopPane.add(internalFrame);
		internalFrame.show();
		
		JInternalFrame poFrame = new POController().getOrderFrame();
		desktopPane.add(poFrame);
		poFrame.show();
	}

	public static void main(String[] args) {
		new MainTestWindow().setVisible(true);
	}
}
