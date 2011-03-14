package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.domain.store.StockTaking;
import org.harper.bookstore.ui.common.ActionThread;
import org.harper.bookstore.ui.common.DateRenderer;
import org.harper.bookstore.ui.common.EnumListCellRenderer;
import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class QueryStockTakingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7749239093149805071L;

	private QueryStockTakingController controller;

	private DateTextField fromDateField;

	private DateTextField toDateField;

	private JComboBox statusCombo;

	private JComboBox siteCombo;

	private JTable resultTable;

	public QueryStockTakingFrame() {
		super();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Query Stock Taking");

		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(3, 4, 10, 10));
		headerPanel.setBorder(new EmptyBorder(10,10,10,10));
		add(headerPanel, BorderLayout.NORTH);

		JLabel fromDateLabel = new JLabel("From Date");
		headerPanel.add(fromDateLabel);

		fromDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		headerPanel.add(fromDateField);

		JLabel toDateLabel = new JLabel("To Date");
		headerPanel.add(toDateLabel);

		toDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		headerPanel.add(toDateField);

		headerPanel.add(new JLabel("Site"));

		siteCombo = new JComboBox();
		siteCombo.setRenderer(new SiteListRenderer());
		headerPanel.add(siteCombo);

		JLabel statusLabel = new JLabel("Status");
		headerPanel.add(statusLabel);

		statusCombo = new JComboBox();
		statusCombo.addItem(null);
		for (StockTaking.Status status : StockTaking.Status.values())
			statusCombo.addItem(status);
		statusCombo.setRenderer(new EnumListCellRenderer(
				StockTaking.Status.class, null, "ALL"));
		headerPanel.add(statusCombo);

		headerPanel.add(new JLabel());
		headerPanel.add(new JLabel());
		headerPanel.add(new JLabel());

		JButton searchButton = new JButton("Search");
		headerPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new ActionThread() {
					@Override
					public void execute() {
						getController().search();
					}

					@Override
					public void exception(Exception ex) {
						JOptionPane.showMessageDialog(
								QueryStockTakingFrame.this, "Error",
								ex.getMessage(), JOptionPane.ERROR_MESSAGE);
					}

				}).start();
			}
		});

		resultTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(QueryStockTakingTableData.class);

		resultTable.setModel(ctm);

		resultTable.setDefaultRenderer(Date.class, new DateRenderer());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public DateTextField getFromDateField() {
		return fromDateField;
	}

	public DateTextField getToDateField() {
		return toDateField;
	}

	public JComboBox getStatusCombo() {
		return statusCombo;
	}

	public JTable getResultTable() {
		return resultTable;
	}

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

	public QueryStockTakingController getController() {
		return controller;
	}

	protected void setController(QueryStockTakingController controller) {
		this.controller = controller;
	}

}
