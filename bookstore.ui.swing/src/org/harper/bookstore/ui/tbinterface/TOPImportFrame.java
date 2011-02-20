package org.harper.bookstore.ui.tbinterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class TOPImportFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1851930142615340176L;

	private TOPImportController controller;

	private DateTextField startDateField;

	private DateTextField stopDateField;

	public TOPImportFrame() {
		super();
		setTitle("TOP Import");
		setSize(450, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new FlowLayout());

		JLabel startDateLabel = new JLabel("Start Date");
		add(startDateLabel);

		startDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		startDateField.setPreferredSize(new Dimension(140, 20));
		add(startDateField);

		JLabel stopDateLabel = new JLabel("Stop Date");
		add(stopDateLabel);

		stopDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		stopDateField.setPreferredSize(new Dimension(140, 20));
		add(stopDateField);

		JButton importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int result = getController().execute();
					JOptionPane.showMessageDialog(TOPImportFrame.this,
							MessageFormat.format("{0} order(s) are imported",
									result), "Done", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(TOPImportFrame.this,
							"Exception occurred:" + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(importButton);

		setVisible(true);
	}

	public TOPImportController getController() {
		return controller;
	}

	public void setController(TOPImportController controller) {
		this.controller = controller;
	}

	public DateTextField getStartDateField() {
		return startDateField;
	}

	public DateTextField getStopDateField() {
		return stopDateField;
	}

}
