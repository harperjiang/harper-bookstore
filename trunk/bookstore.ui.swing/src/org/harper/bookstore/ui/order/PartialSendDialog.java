package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
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

	public PartialSendDialog(PartialSendBean b) {
		super();
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

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

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
