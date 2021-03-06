package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.harper.bookstore.cache.Cache;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.ui.common.LabeledTextArea;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class SOFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883588327069169379L;

	private JButton saveButton;

	private JButton confirmButton;

	private JButton cancelButton;

	private OrderItemTable itemTable;

	private SOHeaderPanel headerPanel;

	private SOController controller;

	private JLabel subtotalAmountLabel;

	private NumTextField transFeeField;

	private LabeledTextArea remarkArea;

	public SOFrame(SOController controller) {
		super("Supply Order");
		setSize(800, 600);

		setLayout(new BorderLayout());

		// Create Tool Bar
		createToolBar();

		createBody();

		this.controller = controller;
		for (StoreSite site : Cache.getInstance().getValidSites()) {
			headerPanel.getSiteCombo().addItem(site);
		}
	}

	protected void createToolBar() {
		JToolBar toolBar = new JToolBar();

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save();
					JOptionPane.showMessageDialog(SOFrame.this, "Order Saved");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(SOFrame.this,
							ee.getMessage(), "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(saveButton);

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.confirm();
					JOptionPane.showMessageDialog(SOFrame.this,
							"Order Confirmed");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(SOFrame.this, "Exception",
							ee.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.cancel();
					JOptionPane.showMessageDialog(SOFrame.this,
							"Order Cancelled");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(SOFrame.this, "Exception",
							ee.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(cancelButton);

		toolBar.setFloatable(false);

		add(toolBar, BorderLayout.NORTH);
	}

	protected void createBody() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		headerPanel = new SOHeaderPanel();
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		itemTable = new OrderItemTable();
		centerPanel.add(itemTable, BorderLayout.CENTER);

		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		JPanel bottomUpPanel = new JPanel();
		bottomUpPanel.setLayout(new FlowLayout());

		JLabel transFeeLabel = new JLabel("Transportation Fee:");
		bottomUpPanel.add(transFeeLabel);

		transFeeField = new NumTextField();
		transFeeField.setPreferredSize(new Dimension(100, 20));
		bottomUpPanel.add(transFeeField);

		JLabel splitterLabel = new JLabel();
		splitterLabel.setPreferredSize(new Dimension(300, 20));
		bottomUpPanel.add(splitterLabel);
		JLabel subtotalLabel = new JLabel("Subtotal:");
		bottomUpPanel.add(subtotalLabel);

		subtotalAmountLabel = new JLabel();
		subtotalAmountLabel.setPreferredSize(new Dimension(100, 20));
		bottomUpPanel.add(subtotalAmountLabel);

		bottomPanel.add(bottomUpPanel, BorderLayout.NORTH);

		remarkArea = new LabeledTextArea("Remark");
		remarkArea.setPreferredSize(new Dimension(0, 100));
		bottomPanel.add(remarkArea, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrderItemTable getItemTable() {
		return itemTable;
	}

	public SOHeaderPanel getHeaderPanel() {
		return headerPanel;
	}

	public SOController getController() {
		return controller;
	}

	public JLabel getSubtotalAmountLabel() {
		return subtotalAmountLabel;
	}

	public void setController(SOController controller) {
		this.controller = controller;
	}

	public JTextArea getRemarkArea() {
		return remarkArea.getTextField();
	}

	public NumTextField getTransFeeField() {
		return transFeeField;
	}

}
