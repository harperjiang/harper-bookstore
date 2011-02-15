package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DOFrame extends JFrame {

	private DeliveryPanel panel;

	private JTextField poNumberField;
	
	private JTable doItemTable;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public DOFrame() {
		super();

		setTitle("Create Delivery Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 400);

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();

		JLabel poLabel = new JLabel("PO Number");
		topPanel.add(poLabel);

		poNumberField = new JTextField();
		poNumberField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(poNumberField);

		add(topPanel, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		panel = new DeliveryPanel();
		tabbedPane.addTab("Delivery Info", panel);

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
			}
		});
		bottomPanel.add(saveButton);

		setVisible(true);
	}

	private DOController controller;

	public DOController getController() {
		return controller;
	}

	public void setController(DOController controller) {
		this.controller = controller;
	}

	public DeliveryPanel getPanel() {
		return panel;
	}

	public JTextField getPoNumberField() {
		return poNumberField;
	}

}
