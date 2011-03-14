package org.harper.bookstore.ui.common;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledTextField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297338127977560359L;

	private JTextField textField;

	private JLabel label;

	public LabeledTextField(String labelName) {
		super();
		// setBorder(LineBorder.createBlackLineBorder());

		GridBagLayout layout = new GridBagLayout();
		
		setLayout(layout);

		label = new JLabel(labelName);
		add(label);

		textField = new JTextField();
		add(textField);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		layout.setConstraints(label, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(textField, c);

	}

	public JTextField getTextField() {
		return textField;
	}

	public JLabel getLabel() {
		return label;
	}

}
