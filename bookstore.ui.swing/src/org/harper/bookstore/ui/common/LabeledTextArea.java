package org.harper.bookstore.ui.common;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LabeledTextArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297338127977560359L;

	private JTextArea textField;

	private JLabel label;

	public LabeledTextArea(String labelName) {
		super();
		// setBorder(LineBorder.createBlackLineBorder());

		GridBagLayout layout = new GridBagLayout();

		setLayout(layout);

		label = new JLabel(labelName);
		add(label);

		textField = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textField);
		add(scrollPane);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(3,3,3,3);
		layout.setConstraints(label, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(3,3,3,3);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(scrollPane, c);

		
	}

	public JTextArea getTextField() {
		return textField;
	}

	public JLabel getLabel() {
		return label;
	}

}
