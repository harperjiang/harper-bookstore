package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class ManageStoreFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5721296117726899188L;

	private ManageStoreController controller;

	private JTable storeTable;

	private CommonTableModel tableModel;

	private JToolBar toolBar;

	public ManageStoreFrame(ManageStoreController controller) {
		super("Query Stock Taking");
		this.controller = controller;

		this.setSize(500, 400);

		setLayout(new BorderLayout());

		createToolBar();
		add(toolBar, BorderLayout.NORTH);

		createStoreTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(storeTable);

		add(scrollPane, BorderLayout.CENTER);
	}

	protected void createStoreTable() {

		tableModel = new CommonTableModel();
		tableModel.initialize(StoreSiteTableData.class);
		tableModel.setTableEditable(true);
		tableModel.setAutoAdd(false);

		this.storeTable = new JTable(tableModel);
		this.storeTable.setDefaultRenderer(Boolean.class,
				new CheckBoxTableRenderer());
	}

	protected void createToolBar() {
		toolBar = new JToolBar();

		JButton addButton = new JButton("Add Site");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewStoreSiteDialog(ManageStoreFrame.this.getManagerWindow())
						.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								controller.load();
							}
						});
			}
		});
		toolBar.add(addButton);

		JButton saveButton = new JButton("Save Change");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
			}
		});
		toolBar.add(saveButton);
	}

	public JTable getStoreTable() {
		return storeTable;
	}

	public CommonTableModel getTableModel() {
		return tableModel;
	}

}
