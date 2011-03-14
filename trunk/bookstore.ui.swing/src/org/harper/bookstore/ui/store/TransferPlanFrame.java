package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class TransferPlanFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846803110879309549L;

	private JTextField statusField;

	private JComboBox fromSiteCombo;

	private JComboBox toSiteCombo;

	private TransferItemTable transferTable;

	private TransferPlanController controller;

	public TransferPlanFrame() {
		super("Transfer Plan");
		setLayout(new BorderLayout());
		setSize(800, 600);

		JToolBar toolBar = new JToolBar();
		toolBar.add(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getController().save();
					JOptionPane.showMessageDialog(TransferPlanFrame.this,
							"Success");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(TransferPlanFrame.this, e1
							.getClass().getSimpleName()
							+ ":" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(new AbstractAction("Confirm") {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					getController().confirm();
					JOptionPane.showMessageDialog(TransferPlanFrame.this,
							"Success");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(TransferPlanFrame.this, e1
							.getClass().getSimpleName()
							+ ":" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getController().cancel();
					JOptionPane.showMessageDialog(TransferPlanFrame.this,
							"Success");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(TransferPlanFrame.this, e1
							.getClass().getSimpleName()
							+ ":" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(toolBar, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		JLabel statusLabel = new JLabel("Status");
		headerPanel.add(statusLabel);

		statusField = new JTextField();
		statusField.setEditable(false);
		statusField.setPreferredSize(new Dimension(100, 20));
		headerPanel.add(statusField);

		JLabel fromSiteLabel = new JLabel("From Site");
		headerPanel.add(fromSiteLabel);

		fromSiteCombo = new JComboBox();
		fromSiteCombo.setRenderer(new SiteListRenderer());
		headerPanel.add(fromSiteCombo);

		JLabel toSiteLabel = new JLabel("To Site");
		headerPanel.add(toSiteLabel);

		toSiteCombo = new JComboBox();
		toSiteCombo.setRenderer(new SiteListRenderer());
		headerPanel.add(toSiteCombo);

		transferTable = new TransferItemTable();
		centerPanel.add(transferTable, BorderLayout.CENTER);
	}

	public JComboBox getFromSiteCombo() {
		return fromSiteCombo;
	}

	public JComboBox getToSiteCombo() {
		return toSiteCombo;
	}

	public TransferItemTable getTransferTable() {
		return transferTable;
	}

	public TransferPlanController getController() {
		return controller;
	}

	public void setController(TransferPlanController controller) {
		this.controller = controller;
	}

	public JTextField getStatusField() {
		return statusField;
	}

}
