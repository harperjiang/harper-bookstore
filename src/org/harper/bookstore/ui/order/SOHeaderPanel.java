package org.harper.bookstore.ui.order;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class SOHeaderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417542166274919535L;

	private JComboBox systemCombo;

	private JTextField customerIdTextField;

	private DateTextField createDateTextField;

	private JTextField numberTextField;

	private JComboBox siteCombo;

	public SOHeaderPanel() {
		super();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		// setBorder(new LineBorder(Color.BLACK, 10));

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);

		JLabel poLabel = new JLabel("SO#:");
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(poLabel, c);
		add(poLabel);

		numberTextField = new JTextField();
		numberTextField.setEditable(false);
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 2.0;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(numberTextField, c);
		add(numberTextField);

		JLabel createDateLabel = new JLabel("Create Date");
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(createDateLabel, c);
		add(createDateLabel);

		createDateTextField = new DateTextField(new SimpleDateFormat(
				"dd/MM/yyyy"));
		createDateTextField.setValue(new Date());
		c.weightx = 2.0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(createDateTextField, c);
		add(createDateTextField);

		JLabel systemLabel = new JLabel("Supplier Position");
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0.0;
		c.insets = new Insets(10, 10, 10, 10);
		layout.setConstraints(systemLabel, c);
		add(systemLabel);

		systemCombo = new JComboBox();
		c.weightx = 2.0;
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(systemCombo, c);
		for (String source : Customer.getSources())
			systemCombo.addItem(source);
		add(systemCombo);

		JLabel customerIdLabel = new JLabel("Supplier Id");
		c.weightx = 0.0;
		c.gridx = 2;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		layout.setConstraints(customerIdLabel, c);
		add(customerIdLabel);

		customerIdTextField = new JTextField();
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(customerIdTextField, c);
		add(customerIdTextField);

		JLabel siteLabel = new JLabel("Store to Site:");
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(siteLabel, c);
		add(siteLabel);

		siteCombo = new JComboBox();
		c.weightx = 1.0;
		c.gridx = 1;
		layout.setConstraints(siteCombo, c);
		siteCombo.setRenderer(new SiteListRenderer());
		add(siteCombo);
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

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

}
