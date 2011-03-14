package org.harper.bookstore.ui.print;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4209835805654311289L;

	private JTextField nameField;

	private JTextArea addressArea;

	private JTextField mobileField;

	public ContactInfoPanel() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();

		JLabel nameLabel = new JLabel("Name");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		layout.setConstraints(nameLabel, c);
		add(nameLabel);

		nameField = new JTextField();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(nameField, c);
		add(nameField);

		JLabel addressLabel = new JLabel("Address");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		layout.setConstraints(addressLabel, c);
		add(addressLabel);

		addressArea = new JTextArea();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		// c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(addressArea);
		layout.setConstraints(sp, c);
		add(sp);

		JLabel mobileLabel = new JLabel("Mobile");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = GridBagConstraints.RELATIVE;
		layout.setConstraints(mobileLabel, c);
		add(mobileLabel);

		mobileField = new JTextField();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(mobileField, c);
		add(mobileField);
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextArea getAddressArea() {
		return addressArea;
	}

	public JTextField getMobileField() {
		return mobileField;
	}

}
