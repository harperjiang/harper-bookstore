package org.harper.bookstore.ui.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class PurchaseReportFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 222186623199359299L;

	private DateTextField startDateField;

	private DateTextField stopDateField;

	private JTable resultTable;

	private PurchaseReportController controller;

	public PurchaseReportFrame() {
		super();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 700);
		setTitle("Purchase Report");

		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		add(headerPanel, BorderLayout.NORTH);

		JLabel startLabel = new JLabel("Start Date");
		headerPanel.add(startLabel);

		startDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		startDateField.setPreferredSize(new Dimension(150, 20));
		headerPanel.add(startDateField);

		JLabel stopLabel = new JLabel("Stop Date");
		headerPanel.add(stopLabel);

		stopDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		stopDateField.setPreferredSize(new Dimension(150, 20));
		headerPanel.add(stopDateField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});
		headerPanel.add(searchButton);

		resultTable = new JTable();

		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(PurchaseReportTableData.class);
		resultTable.setModel(ctm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public PurchaseReportController getController() {
		return controller;
	}

	public void setController(PurchaseReportController controller) {
		this.controller = controller;
	}

	public DateTextField getStartDateField() {
		return startDateField;
	}

	public DateTextField getStopDateField() {
		return stopDateField;
	}

	public JTable getResultTable() {
		return resultTable;
	}

}
