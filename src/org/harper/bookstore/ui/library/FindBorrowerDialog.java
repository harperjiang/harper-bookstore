package org.harper.bookstore.ui.library;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.harper.bookstore.domain.profile.Borrower;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class FindBorrowerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253370106303289120L;

	private FindBorrowerController controller;

	private JTextField nameField;

	private JTextField companyField;

	private JTable borrowerTable;

	public FindBorrowerDialog(JFrame parent) {
		super(parent, true);
		setResizable(false);
		setSize(300, 500);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//		JPanel headerPanel = new JPanel();
//		add(headerPanel, BorderLayout.NORTH);
//
//		headerPanel.setLayout(new FlowLayout());

		add(new JLabel("Borrower Name"));

		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(150, 20));
		add(nameField);

		add(new JLabel("Borrower Company"));
		companyField = new JTextField();
		companyField.setPreferredSize(new Dimension(150, 20));
		add(companyField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});
		add(searchButton);

		borrowerTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(BorrowerTableData.class);
		borrowerTable.setModel(ctm);
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(borrowerTable);
		sp.setPreferredSize(new Dimension(290,350));
		add(sp, BorderLayout.CENTER);
		borrowerTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					int selected = borrowerTable.getSelectedRow();
					if (-1 != selected) {
						AbstractTableData atd = (AbstractTableData) ((CommonTableModel) borrowerTable
								.getModel()).getData().get(selected);
						getController().getBean().setSelected((Borrower)atd.getBean());
						FindBorrowerDialog.this.dispose();
					}
				}
			}
		});
	}

	public FindBorrowerController getController() {
		return controller;
	}

	public void setController(FindBorrowerController controller) {
		this.controller = controller;
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getCompanyField() {
		return companyField;
	}

	public JTable getBorrowerTable() {
		return borrowerTable;
	}

}
