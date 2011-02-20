package org.harper.bookstore.ui.setting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.harper.bookstore.ui.print.ContactInfoPanel;

public class ContactInfoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7508918330024244886L;

	private ContactInfoController controller;

	private ContactInfoPanel panel;

	public ContactInfoFrame() {
		super();

		setTitle("Contact Info Setting");
		setSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		panel = new ContactInfoPanel();
		add(panel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getController().save();
				ContactInfoFrame.this.dispose();
			}
		});

		bottomPanel.add(saveButton);

		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	public ContactInfoPanel getPanel() {
		return panel;
	}

	public ContactInfoController getController() {
		return controller;
	}

	public void setController(ContactInfoController controller) {
		this.controller = controller;
	}

}
