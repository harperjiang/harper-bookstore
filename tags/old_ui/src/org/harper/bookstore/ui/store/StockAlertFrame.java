package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.store.StockAlert;
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
		alertTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());

		alertTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() <= 1)
					return;
				int selected = alertTable.getSelectedRow();
				StockAlert alert = controller.getBean().getAlerts()
						.get(selected);
				alertDialog(alert);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(alertTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	protected void alertDialog(StockAlert alert) {
		NewStockAlertDialog dialog = new NewStockAlertDialog(
				StockAlertFrame.this, alert);
		if (dialog.isOkay()) {
			StockAlert newAlert = dialog.getBean();
			// Insert new alert record
			try {
				getController().addAlert(newAlert);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(StockAlertFrame.this, "Error",
						ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void createToolBar() {
		toolBar = new JToolBar();

		JButton addButton = new JButton("Add");
		toolBar.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alertDialog(null);
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

	public JTable getAlertTable() {
		return alertTable;
	}

}
