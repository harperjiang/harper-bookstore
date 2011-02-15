package org.harper.bookstore.ui.order;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class POHeaderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417542166274919535L;

	private JComboBox systemCombo;

	private JTextField customerIdTextField;

	private DateTextField createDateTextField;

	private JTextField numberTextField;

	private JTextField statusTextField;

	private JComboBox siteCombo;

	private JTextField refnoTextField;

	private JTextField refStatusTextField;

	public POHeaderPanel() {
		super();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		// setBorder(new LineBorder(Color.BLACK, 10));

		JLabel poLabel = new JLabel("PO#:");
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.gridx = 0;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(poLabel, c);
		add(poLabel);

		numberTextField = new JTextField();
		numberTextField.setEditable(false);
		c.gridx = 1;
		c.weightx = 2.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(numberTextField, c);
		add(numberTextField);

		JLabel createDateLabel = new JLabel("Create Date");
		c.weightx = 1.0;
		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(createDateLabel, c);
		add(createDateLabel);

		createDateTextField = new DateTextField(new SimpleDateFormat(
				"dd/MM/yyyy"));
		createDateTextField.setValue(new Date());
		c.weightx = 2.0;
		c.gridx = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(createDateTextField, c);
		add(createDateTextField);

		JLabel systemLabel = new JLabel("Customer Position");
		c.fill = GridBagConstraints.NONE;
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1.0;
		c.insets = new Insets(10, 10, 10, 10);
		layout.setConstraints(systemLabel, c);
		add(systemLabel);

		systemCombo = new JComboBox();
		c.weightx = 2.0;
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(systemCombo, c);
		for (String source : Customer.getSources())
			systemCombo.addItem(source);
		add(systemCombo);

		JLabel customerIdLabel = new JLabel("Customer Id");
		c.weightx = 1.0;
		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(customerIdLabel, c);
		add(customerIdLabel);

		customerIdTextField = new JTextField();
		c.weightx = 1.0;
		c.gridx = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(customerIdTextField, c);
		add(customerIdTextField);

		JLabel statusLabel = new JLabel("Status");
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(statusLabel, c);
		add(statusLabel);

		statusTextField = new JTextField();
		statusTextField.setEditable(false);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(statusTextField, c);
		add(statusTextField);

		JLabel siteLabel = new JLabel("Site");
		c.weightx = 0.0;
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(siteLabel, c);
		add(siteLabel);

		siteCombo = new JComboBox();
		c.weightx = 1.0;
		c.gridx = 3;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(siteCombo, c);
		siteCombo.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = -8062361344485367288L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel superLabel = (JLabel) super
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);
				if (value instanceof StoreSite)
					superLabel.setText(((StoreSite) value).getName());
				return superLabel;
			}

		});
		add(siteCombo);
		
		JLabel refNoLabel = new JLabel("Ref No.");
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(refNoLabel, c);
		add(refNoLabel);
		
		refnoTextField = new JTextField();
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(refnoTextField, c);
		refnoTextField.setEditable(false);
		add(refnoTextField);
		
		JLabel refStatusLabel = new JLabel("Ref Status.");
		c.weightx = 0.0;
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(refStatusLabel, c);
		add(refStatusLabel);
		
		refStatusTextField = new JTextField();
		c.weightx = 1.0;
		c.gridx = 3;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(refStatusTextField, c);
		refStatusTextField.setEditable(false);
		add(refStatusTextField);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JComboBox getSystemCombo() {
		return systemCombo;
	}

	public JTextField getCustomerIdTextField() {
		return customerIdTextField;
	}

	public DateTextField getCreateDateTextField() {
		return createDateTextField;
	}

	public JTextField getNumberTextField() {
		return numberTextField;
	}

	public JTextField getStatusTextField() {
		return statusTextField;
	}

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

	public JTextField getRefnoTextField() {
		return refnoTextField;
	}

	public JTextField getRefStatusTextField() {
		return refStatusTextField;
	}

}
