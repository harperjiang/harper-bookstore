package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.harper.frm.gui.swing.manager.BindingManager;

public class PartialSendDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449030288959163630L;

	JTable itemTable;

	BindingManager manager;

	PartialSendBean bean;

	boolean okay;

	public PartialSendDialog(JFrame parent, PartialSendBean b) {
		super(parent);
		setTitle("Select Items to be sent");
		setSize(600, 400);
		setModal(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(PartialSendItemTableData.class);
		ctm.setCellEditable(3, true);
		itemTable = new JTable();
		itemTable.setModel(ctm);
		itemTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		itemTable.setDefaultEditor(Integer.TYPE, new DefaultCellEditor(
				new NumTextField()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(itemTable);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		DateTextField dateField = new DateTextField(new SimpleDateFormat(
				"yyyy-MM-dd"));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));

		JToolBar toolBar = new JToolBar();
		add(topPanel, BorderLayout.NORTH);

		topPanel.add(toolBar);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new FlowLayout());
		newPanel.add(new JLabel("Choose Delivery Date:"));
		newPanel.add(dateField);
		topPanel.add(newPanel);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okay = true;
				PartialSendDialog.this.dispose();
			}
		});
		toolBar.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PartialSendDialog.this.dispose();
			}
		});
		toolBar.add(cancelButton);

		bean = null == b ? new PartialSendBean() : b;
		manager = new BindingManager(bean);
		manager.addBinding(new TableBinding(itemTable, "beans"));
		manager.addBinding(dateField.new DateTextBinding("sendDate"));
		manager.loadAll();

		setVisible(true);
	}

	public PartialSendBean getBean() {
		return bean;
	}

	public boolean isOkay() {
		return okay;
	}

}
