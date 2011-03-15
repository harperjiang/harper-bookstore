package org.harper.bookstore.ui.order;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.harper.bookstore.ui.common.ExpressCompanyCombo;

public class DeliveryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7686826387337752821L;

	private JComboBox companyCombo;

	private JTextField orderNumberField;

	private JTextField nameField;

	private JTextArea addressArea;

	private JTextField emailField;

	private JTextField phoneField;

	private JTextField mobileField;

	public DeliveryPanel() {
		super();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		JLabel companyLabel = new JLabel(Messages.getString("DeliveryPanel.express_company")); //$NON-NLS-1$
		add(companyLabel);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 10, 2);
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(companyLabel, c);

		companyCombo = new ExpressCompanyCombo();
		add(companyCombo);
		c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 10, 2);
		c.gridx = 1;
		c.gridy = 0;
		layout.setConstraints(companyCombo, c);

		orderNumberField = new JTextField();
		orderNumberField.setPreferredSize(new Dimension(250, 25));
		add(orderNumberField);
		c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 10, 2);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 6;
		c.anchor = GridBagConstraints.NORTHWEST;
		layout.setConstraints(orderNumberField, c);

		JLabel padLabel = new JLabel();
		add(padLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1;
		layout.setConstraints(padLabel, c);

		JLabel nameLabel = new JLabel(Messages.getString("DeliveryPanel.name")); //$NON-NLS-1$
		add(nameLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 1;
		layout.setConstraints(nameLabel, c);

		nameField = new JTextField();
		add(nameField);
		nameField.setPreferredSize(new Dimension(100, 20));
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 1;
		c.gridy = 1;
		layout.setConstraints(nameField, c);

		JLabel mobileLabel = new JLabel(Messages.getString("DeliveryPanel.mobile")); //$NON-NLS-1$
		add(mobileLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(mobileLabel, c);

		mobileField = new JTextField();
		add(mobileField);
		mobileField.setPreferredSize(new Dimension(150, 20));
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 3;
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		layout.setConstraints(mobileField, c);

		JLabel phoneLabel = new JLabel(Messages.getString("DeliveryPanel.phone")); //$NON-NLS-1$
		add(phoneLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 4;
		c.gridy = 1;
		layout.setConstraints(phoneLabel, c);

		phoneField = new JTextField();
		add(phoneField);
		phoneField.setPreferredSize(new Dimension(150, 20));
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 5;
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		layout.setConstraints(phoneField, c);

		JLabel emailLabel = new JLabel(Messages.getString("DeliveryPanel.email")); //$NON-NLS-1$
		add(emailLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 6;
		c.gridy = 1;
		layout.setConstraints(emailLabel, c);

		emailField = new JTextField();
		add(emailField);
		emailField.setPreferredSize(new Dimension(150, 20));
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 7;
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		layout.setConstraints(emailField, c);

		JLabel addressLabel = new JLabel(Messages.getString("DeliveryPanel.address")); //$NON-NLS-1$
		add(addressLabel);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 2;
		layout.setConstraints(addressLabel, c);

		addressArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(addressArea);
		scrollPane.setPreferredSize(new Dimension(100, 80));
		add(scrollPane);
		c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 5, 2);
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 2;
		c.gridwidth = 5;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(scrollPane, c);

		add(new JLabel());

		padLabel = new JLabel();
		add(padLabel);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 6;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(padLabel, c);
	}

	public JComboBox getCompanyCombo() {
		return companyCombo;
	}

	public JTextField getOrderNumberField() {
		return orderNumberField;
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextArea getAddressArea() {
		return addressArea;
	}

	public JTextField getEmailField() {
		return emailField;
	}

	public JTextField getPhoneField() {
		return phoneField;
	}

	public JTextField getMobileField() {
		return mobileField;
	}

}
