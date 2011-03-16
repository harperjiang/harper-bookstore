package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.deliver.DeliveryItem;
import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.domain.deliver.ReceiveItem;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class ViewDOFrame extends JPowerWindowEditor {

	private DeliveryPanel panel;

	private JTextField poNumberField;

	private JTextField statusField;

	private JTable doItemTable;

	/*	
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public ViewDOFrame() {
		super(Messages.getString("ViewDOFrame.title")); //$NON-NLS-1$

		setSize(800, 700);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JButton saveButton = new JButton(
				Messages.getString("ViewDOFrame.btn_save")); //$NON-NLS-1$
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save(false);
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							Messages.getString("ViewDOFrame.msg_save_success")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							ee.getMessage(),
							Messages.getString("ViewDOFrame.msg_save_fail"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(saveButton);

		JButton deliverButton = new JButton(
				Messages.getString("ViewDOFrame.btn_deliver")); //$NON-NLS-1$
		deliverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.deliver();
					JOptionPane.showMessageDialog(ViewDOFrame.this, Messages
							.getString("ViewDOFrame.msg_deliver_success")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							ee.getMessage(),
							Messages.getString("ViewDOFrame.msg_deliver_fail"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(deliverButton);

		JButton fallbackButton = new JButton(
				Messages.getString("ViewDOFrame.btn_fallback")); //$NON-NLS-1$
		fallbackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ROController roc = new ROController();
				DeliveryOrder delivery = getController().getBean()
						.getDelivery();
				roc.getBean().setDelivery(delivery);
				for (DeliveryItem item : delivery.getItems()) {
					ReceiveItem ri = new ReceiveItem();
					ri.setBook(item.getBook());
					ri.setCount(0);
					roc.getBean().addItems(ri);
					ri.setUnitCost(item.getUnitCost());
				}
				getManagerWindow().addEditor(roc.getComponent());
				try {
					// controller.fallback();
					// JOptionPane.showMessageDialog(ViewDOFrame.this, Messages
					//							.getString("ViewDOFrame.msg_fallback_success")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					// JOptionPane.showMessageDialog(
					// ViewDOFrame.this,
					// ee.getMessage(),
					//							Messages.getString("ViewDOFrame.msg_fallback_fail"), //$NON-NLS-1$
					// JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(fallbackButton);

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);

		topPanel.setLayout(new FlowLayout());
		JLabel statusLabel = new JLabel(
				Messages.getString("ViewDOFrame.label_status")); //$NON-NLS-1$
		topPanel.add(statusLabel);
		statusField = new JTextField();
		statusField.setEditable(false);
		topPanel.add(statusField);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		doItemTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(ViewDeliveryItemTableData.class);
		doItemTable.setModel(ctm);

		doItemTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		doItemTable.setDefaultRenderer(BigDecimal.class,
				new DefaultTableCellRenderer());
		doItemTable.setDefaultRenderer(Boolean.TYPE,
				new CheckBoxTableRenderer());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(doItemTable);
		centerPanel.add(scrollPane);

		panel = new DeliveryPanel();
		centerPanel.add(panel);
	}

	private ViewDOController controller;

	public ViewDOController getController() {
		return controller;
	}

	public void setController(ViewDOController controller) {
		this.controller = controller;
	}

	public DeliveryPanel getPanel() {
		return panel;
	}

	public JTextField getPoNumberField() {
		return poNumberField;
	}

	public JTable getDoItemTable() {
		return doItemTable;
	}

	public JTextField getStatusField() {
		return statusField;
	}

}
