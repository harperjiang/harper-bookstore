package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.ui.common.DateRenderer;
import org.harper.bookstore.ui.common.SiteTableRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;

public class ViewTransferFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8480311669454668263L;

	private ViewTransferController controller;

	private JTable transferTable;

	public ViewTransferFrame() {
		super();
		setTitle("View Transfer");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);

		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new FlowLayout());

		JButton searchButton = new JButton("Search");
		headerPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});

		transferTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(TransferTableData.class);
		transferTable.setModel(ctm);

		transferTable.setDefaultRenderer(String.class,
				new DefaultTableCellRenderer());
		transferTable.setDefaultRenderer(Date.class,
				new DateRenderer());
		transferTable.setDefaultRenderer(StoreSite.class,
				new SiteTableRenderer());
		transferTable.setDefaultRenderer(Transfer.Status.class,
				new DefaultTableCellRenderer());

		transferTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		transferTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selected = transferTable.getSelectedRow();
					if (-1 != selected) {
						Transfer trans = (Transfer)((AbstractTableData) ((CommonTableModel) transferTable
								.getModel()).getData().get(selected)).getBean();
						new TransferPlanController(trans);
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(transferTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public ViewTransferController getController() {
		return controller;
	}

	public void setController(ViewTransferController controller) {
		this.controller = controller;
	}

	public JTable getTransferTable() {
		return transferTable;
	}

}
