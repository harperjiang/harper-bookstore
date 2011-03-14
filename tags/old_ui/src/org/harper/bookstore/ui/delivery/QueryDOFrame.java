package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.ui.common.EnumListCellRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;

public class QueryDOFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 732562746905953042L;

	private QueryDOController controller;

	private DateTextField fromDateField;

	private DateTextField toDateField;

	private JTextField consigneeNameField;

	private JTextField poNumberField;

	private JTextField poCustomerIdField;

	private JComboBox statusCombo;

	private JTable queryDoTable;

	public QueryDOFrame() {
		super();
		setTitle("Query Delivery Order");
		setSize(800, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		createTopPanel();

		queryDoTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(DeliveryOrderTableData.class);
		queryDoTable.setModel(ctm);

		queryDoTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() <= 1)
					return;
				int selected = queryDoTable.getSelectedRow();
				DeliveryOrder order = controller.getBean().getOrders()
						.get(selected);
				new ViewDOController(order);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(queryDoTable);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public QueryDOController getController() {
		return controller;
	}

	public void setController(QueryDOController controller) {
		this.controller = controller;
	}

	protected void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.setLayout(new GridLayout(4, 4, 5, 5));

		JLabel fromDateLabel = new JLabel("From Date");
		topPanel.add(fromDateLabel);
		fromDateLabel.setPreferredSize(new Dimension(120, 25));

		fromDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		topPanel.add(fromDateField);
		fromDateField.setPreferredSize(new Dimension(150, 25));

		JLabel toDateLabel = new JLabel("To Date");
		topPanel.add(toDateLabel);
		toDateLabel.setPreferredSize(new Dimension(120, 25));

		toDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		topPanel.add(toDateField);
		toDateField.setPreferredSize(new Dimension(150, 25));

		JLabel consigneeNameLabel = new JLabel("Consignee Name");
		topPanel.add(consigneeNameLabel);
		consigneeNameLabel.setPreferredSize(new Dimension(120, 25));

		consigneeNameField = new JTextField();
		consigneeNameField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(consigneeNameField);

		JLabel poNumberLabel = new JLabel("PO Number");
		topPanel.add(poNumberLabel);

		poNumberField = new JTextField();
		topPanel.add(poNumberField);

		JLabel poCustomerIdLabel = new JLabel("PO Customer ID");
		topPanel.add(poCustomerIdLabel);

		poCustomerIdField = new JTextField();
		topPanel.add(poCustomerIdField);

		JLabel statusLabel = new JLabel("Status");
		topPanel.add(statusLabel);

		statusCombo = new JComboBox();
		statusCombo.addItem(null);
		for (DeliveryOrder.Status sta : DeliveryOrder.Status.values())
			statusCombo.addItem(sta);
		statusCombo
				.setRenderer(new EnumListCellRenderer(
						DeliveryOrder.Status.class,
						ResourceBundle
								.getBundle("org.harper.bookstore.ui.delivery.DOStatus"),
						"ALL"));
		topPanel.add(statusCombo);


		JButton searchButton = new JButton("Search");
		topPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});

		add(topPanel, BorderLayout.NORTH);
	}

	public DateTextField getFromDateField() {
		return fromDateField;
	}

	public DateTextField getToDateField() {
		return toDateField;
	}

	public JTextField getConsigneeNameField() {
		return consigneeNameField;
	}

	public JTextField getPoNumberField() {
		return poNumberField;
	}

	public JTextField getPoCustomerIdField() {
		return poCustomerIdField;
	}

	public JTable getQueryDoTable() {
		return queryDoTable;
	}

	public JComboBox getStatusCombo() {
		return statusCombo;
	}

}
