package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.harper.bookstore.ui.common.ExpressCompanyCombo;
import org.harper.bookstore.ui.print.ContactInfoPanel;

public class PrintExpressDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4117366689576844561L;

	private ContactInfoPanel fromPanel;

	private JComboBox companyCombo;

	public PrintExpressDialog() {
		setTitle("Print Express");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(300, 200));

		setLayout(new BorderLayout());

		JPanel upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout());
		add(upPanel, BorderLayout.NORTH);

		companyCombo = new ExpressCompanyCombo();
		companyCombo.setPreferredSize(new Dimension(200, 25));
		upPanel.add(companyCombo);

		fromPanel = new ContactInfoPanel();
		add(fromPanel, BorderLayout.CENTER);

		JPanel downPanel = new JPanel();
		downPanel.setLayout(new FlowLayout());

		add(downPanel, BorderLayout.SOUTH);

		JButton okayButton = new JButton("Confirm");
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PrintExpressDialog.this.setVisible(false);
				PrintExpressDialog.this.dispose();
			}

		});
		downPanel.add(okayButton);

		setVisible(true);
	}

	public static void main(String[] args) {
		new PrintExpressDialog();
	}
}
