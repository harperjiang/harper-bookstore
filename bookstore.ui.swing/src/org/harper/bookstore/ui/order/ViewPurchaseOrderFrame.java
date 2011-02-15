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
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class ViewPurchaseOrderFrame extends JFrame {

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

	JComboBox deliveryStatusCombo;

	public ViewPurchaseOrderFrame() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(Messages.getString("ViewPurchaseOrderFrame.title")); //$NON-NLS-1$
		setSize(1000, 700);

		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		add(mainPanel, BorderLayout.CENTER);

		JPanel headerPanel = new JPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		headerPanel.setLayout(new GridLayout(4, 2, 5, 5));
		headerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel orderNumLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.order_num")); //$NON-NLS-1$
		headerPanel.add(orderNumLabel);

		orderNumField = new JTextField();
		orderNumField.setPreferredSize(new Dimension(120, 20));
		headerPanel.add(orderNumField);

		JLabel statusLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.order_status")); //$NON-NLS-1$
		headerPanel.add(statusLabel);

		statusCombo = new JComboBox();
		statusCombo.addItem(Messages.getString("ViewPurchaseOrderFrame.status_all")); //$NON-NLS-1$
		for (Order.Status status : Order.Status.values())
			statusCombo.addItem(status.name());
		headerPanel.add(statusCombo);

		JLabel startDateLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.start_date")); //$NON-NLS-1$
		headerPanel.add(startDateLabel);

		startDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		startDateField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(startDateField);

		JLabel stopDateLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.stop_date")); //$NON-NLS-1$
		headerPanel.add(stopDateLabel);

		stopDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		stopDateField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(stopDateField);

		JLabel partyIdLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.cust_id")); //$NON-NLS-1$
		headerPanel.add(partyIdLabel);

		partyIdField = new JTextField();
		partyIdField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(partyIdField);

		JLabel deliveryStatusLabel = new JLabel(Messages.getString("ViewPurchaseOrderFrame.delivery_status")); //$NON-NLS-1$
		headerPanel.add(deliveryStatusLabel);

		deliveryStatusCombo = new JComboBox();
		deliveryStatusCombo.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(deliveryStatusCombo);

		JButton searchButton = new JButton(Messages.getString("ViewPurchaseOrderFrame.btn_search")); //$NON-NLS-1$
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});
		headerPanel.add(searchButton);

		JButton printOrderButton = new JButton(Messages.getString("ViewPurchaseOrderFrame.print_order")); //$NON-NLS-1$
		printOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		headerPanel.add(printOrderButton);

		JButton printExpressButton = new JButton(Messages.getString("ViewPurchaseOrderFrame.print_express")); //$NON-NLS-1$
		printExpressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		headerPanel.add(printExpressButton);

		orderTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(PurchaseOrderTableData.class);
		orderTable.setModel(ctm);

		orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		for (int i = 0; i < ctm.getColumnCount(); i++)
			orderTable.getColumnModel().getColumn(i).setPreferredWidth(
					ctm.getColumnWidth(i));

		// TableColumnModel
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

	private ViewPurchaseOrderController controller;

	public ViewPurchaseOrderController getController() {
		return controller;
	}

	public void setController(ViewPurchaseOrderController controller) {
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

	public JComboBox getDeliveryStatusCombo() {
		return deliveryStatusCombo;
	}

}
