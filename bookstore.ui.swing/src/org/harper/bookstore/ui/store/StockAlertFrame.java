package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;

public class StockAlertFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6598868018622968323L;

	private StockAlertController controller;

	private JTable alertTable;

	private JToolBar toolBar;

	public StockAlertFrame() {
		super();

		setTitle("Stock Alert");
		setSize(600, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UIStandard.standardFrame(this);

		setLayout(new BorderLayout());

		createToolBar();

		alertTable = new JTable();

		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(StockAlertTableData.class);
		alertTable.setModel(ctm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(alertTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	protected void createToolBar() {
		toolBar = new JToolBar();

		JButton addButton = new JButton("Add");
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewStockAlertDialog(StockAlertFrame.this, null);
			}
		});

		JButton saveButton = new JButton("Save");
		toolBar.add(saveButton);

		JButton deleteButton = new JButton("Delete");
		toolBar.add(deleteButton);

		add(toolBar, BorderLayout.NORTH);
	}

	public StockAlertController getController() {
		return controller;
	}

	public void setController(StockAlertController controller) {
		this.controller = controller;
	}

}
