package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.bookstore.ui.order.Messages;
import org.harper.bookstore.ui.order.POFrame;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;

public class ViewDOFrame extends JFrame {

	private DeliveryPanel panel;

	private JTextField poNumberField;

	private JTable doItemTable;
	
	/*	
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public ViewDOFrame() {
		super();

		setTitle("View Delivery Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);

		setLayout(new BorderLayout());
		
		JToolBar toolBar = new JToolBar();
		add(toolBar,BorderLayout.NORTH);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save(false);
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							"Saved"); 
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							ee.getMessage(),
							"Failed to Save", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(saveButton);
		
		JButton deliverButton = new JButton("Deliver");
		deliverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save(false);
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							"Saved"); 
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							ee.getMessage(),
							"Failed to Save", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(deliverButton);
		
		JButton fallbackButton = new JButton("Fallback");
		fallbackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save(false);
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							"Saved"); 
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ViewDOFrame.this,
							ee.getMessage(),
							"Failed to Save", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(fallbackButton);
		
		JPanel mainPanel = new JPanel();
		add(mainPanel,BorderLayout.CENTER);
		
		mainPanel.setLayout(new GridLayout(2, 1));

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
		scrollPane.setPreferredSize(new Dimension(800, 300));

		mainPanel.add(scrollPane);

		panel = new DeliveryPanel();
		panel.setPreferredSize(new Dimension(800, 300));
		mainPanel.add("Delivery Info", panel);

		setVisible(true);
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

}
