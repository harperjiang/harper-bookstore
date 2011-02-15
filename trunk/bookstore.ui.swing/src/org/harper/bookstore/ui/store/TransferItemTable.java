package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

/**
 * OrderItemTable is a table with an JTextField for ISBN input
 * 
 * @author Administrator
 * 
 */
public class TransferItemTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1106314954455483203L;

	private JTable itemTable;

	private CommonTableModel tableModel;

	private ISBNTextField isbnTextField;

	private TransferItemController controller;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getItemTable() {
		return itemTable;
	}

	public CommonTableModel getTableModel() {
		return tableModel;
	}

	public JTextField getIsbnTextField() {
		return isbnTextField;
	}

	public TransferItemController getController() {
		return controller;
	}

	public TransferItemTable() {
		super();
		setLayout(new BorderLayout());

		JPanel isbnPanel = new JPanel();
		isbnPanel.setLayout(new FlowLayout());

		JLabel isbnLabel = new JLabel("Input ISBN");
		isbnPanel.add(isbnLabel);

		isbnTextField = new ISBNTextField();
		isbnTextField.setPreferredSize(new Dimension(300, 20));
		isbnPanel.add(isbnTextField);

		add(isbnPanel, BorderLayout.NORTH);

		itemTable = new JTable();
		itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = new CommonTableModel();
		tableModel.initialize(TransferItemTableData.class);
		tableModel.setCellEditable(2, true);
		itemTable.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(itemTable);
		add(scrollPane, BorderLayout.CENTER);

		// Renderer & Editor
		itemTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		itemTable.setDefaultRenderer(BigDecimal.class,
				new DefaultTableCellRenderer());
		itemTable.setDefaultEditor(Integer.TYPE, new DefaultCellEditor(
				new NumTextField()));
		itemTable.setDefaultEditor(BigDecimal.class, new DefaultCellEditor(
				new NumTextField()));

		controller = new TransferItemController(this, null);

		isbnTextField.setCallback(new Callback() {
			@Override
			public void call(Book isbn) {
				controller.add(isbn);
			}

			@Override
			public void call(List<Book> books) {
				for (Book book : books)
					call(book);
			}

			@Override
			public void exception(Exception e) {
				JOptionPane.showMessageDialog(TransferItemTable.this, e
						.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		});

		itemTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// System.out.println(e.getFirstIndex()+":"+e.getLastIndex());
						itemTable.scrollRectToVisible(new Rectangle(0,
								itemTable.getRowHeight()
										* itemTable.getSelectedRow(), 10, 10));
					}
				});
		itemTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (127 == e.getKeyChar() && -1 != itemTable.getSelectedRow()) {
					controller.remove(itemTable.getSelectedRow());
				}
			}
		});
	}
}
