package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class ViewOrderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8544961738213795757L;

	JComboBox orderTypeCombo;

	JComboBox statusCombo;

	DateTextField startDateField;

	DateTextField stopDateField;

	JTextField partyIdField;

	JTextField orderNumField;

	JTable orderTable;

	public ViewOrderFrame() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("View Orders");
		setSize(1000, 700);

		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		add(mainPanel, BorderLayout.CENTER);

		JPanel headerPanel = new JPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		headerPanel.setLayout(new GridLayout(4, 2, 5, 5));

		JLabel orderTypeLabel = new JLabel("Order Type");
		headerPanel.add(orderTypeLabel);

		orderTypeCombo = new JComboBox();
		orderTypeCombo.addItem("PO");
		orderTypeCombo.addItem("SO");
		headerPanel.add(orderTypeCombo);

		JLabel statusLabel = new JLabel("Order Status");
		headerPanel.add(statusLabel);

		statusCombo = new JComboBox();
		statusCombo.addItem("ALL");
		for (Order.Status status : Order.Status.values())
			statusCombo.addItem(status.name());
		headerPanel.add(statusCombo);

		JLabel startDateLabel = new JLabel("Start Date");
		headerPanel.add(startDateLabel);

		startDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		startDateField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(startDateField);

		JLabel stopDateLabel = new JLabel("Stop Date");
		headerPanel.add(stopDateLabel);

		stopDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		stopDateField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(stopDateField);

		JLabel partyIdLabel = new JLabel("Customer/Supplier Id");
		headerPanel.add(partyIdLabel);

		partyIdField = new JTextField();
		partyIdField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(partyIdField);

		JLabel orderNumLabel = new JLabel("Order Num:");
		headerPanel.add(orderNumLabel);

		orderNumField = new JTextField();
		orderNumField.setPreferredSize(new Dimension(120, 20));
		headerPanel.add(orderNumField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});
		headerPanel.add(searchButton);
		
		orderTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(OrderTableData.class);
		orderTable.setModel(ctm);

		orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		for (int i = 0; i < ctm.getColumnCount(); i++)
			orderTable.getColumnModel().getColumn(i).setPreferredWidth(
					ctm.getColumnWidth(i));

//		TableColumnModel
		orderTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() <= 1)
					return;
				int selected = orderTable.getSelectedRow();
				Order order = controller.getBean().getSearchResults().get(
						selected);
				if (order instanceof PurchaseOrder) {
					new POController((PurchaseOrder) order);
				}
				if (order instanceof SupplyOrder) {
					new SOController((SupplyOrder) order);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(orderTable);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	private ViewOrderController controller;

	public ViewOrderController getController() {
		return controller;
	}

	public void setController(ViewOrderController controller) {
		this.controller = controller;
	}

	public JComboBox getOrderTypeCombo() {
		return orderTypeCombo;
	}

	public JComboBox getStatusCombo() {
		return statusCombo;
	}

	public DateTextField getStartDateField() {
		return startDateField;
	}

	public DateTextField getStopDateField() {
		return stopDateField;
	}

	public JTextField getPartyIdField() {
		return partyIdField;
	}

	public JTable getOrderTable() {
		return orderTable;
	}

	public JTextField getOrderNumField() {
		return orderNumField;
	}

}
