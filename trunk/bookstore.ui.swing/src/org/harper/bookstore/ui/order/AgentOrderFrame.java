package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.SourceComboBox;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class AgentOrderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8942014904344517542L;

	private AgentOrderController controller;

	private ItemController<OrderItem> itemController;

	private JTextField customerField;

	private JTextField supplierField;

	private SourceComboBox customerSource;

	private SourceComboBox supplierSource;

	public AgentOrderFrame() {
		super();
		setTitle("Agent Order");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		toolBar.add(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save();
					JOptionPane.showMessageDialog(AgentOrderFrame.this,
							"Order Saved");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(AgentOrderFrame.this, e1
							.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(new JLabel("Customer"));

		customerSource = new SourceComboBox();
		headerPanel.add(customerSource);
		
		customerField = new JTextField();
		customerField.setPreferredSize(new Dimension(200, 20));
		headerPanel.add(customerField);

		headerPanel.add(new JLabel("Supplier"));
		
		supplierSource = new SourceComboBox();
		headerPanel.add(supplierSource);

		supplierField = new JTextField();
		supplierField.setPreferredSize(new Dimension(200, 20));
		headerPanel.add(supplierField);

		itemController = new ItemController<OrderItem>((List<OrderItem>) null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(AgentOrderItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(2, true);
						ctm.setCellEditable(3, true);
						ctm.setCellEditable(4, true);

						table.setDefaultRenderer(Integer.TYPE,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(BigDecimal.class,
								new DefaultTableCellRenderer());
						table.setDefaultEditor(Integer.TYPE,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(BigDecimal.class,
								new DefaultCellEditor(new NumTextField()));
					}

				}) {

			@Override
			protected OrderItem createItem(Book book) {
				OrderItem item = new OrderItem();
				item.setBook(book);
				item.setUnitPrice(getController().getListPrice(book));
				item.setCount(1);
				return item;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new AgentOrderItemTableData(item);
			}
		};
		centerPanel.add(itemController.getView(), BorderLayout.CENTER);

		setVisible(true);
	}

	public AgentOrderController getController() {
		return controller;
	}

	public void setController(AgentOrderController controller) {
		this.controller = controller;
	}

	public ItemController<OrderItem> getItemController() {
		return itemController;
	}

	public JTextField getCustomerField() {
		return customerField;
	}

	public JTextField getSupplierField() {
		return supplierField;
	}

	public SourceComboBox getCustomerSource() {
		return customerSource;
	}

	public SourceComboBox getSupplierSource() {
		return supplierSource;
	}

}
