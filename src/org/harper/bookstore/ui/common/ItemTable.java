package org.harper.bookstore.ui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.harper.bookstore.domain.Item;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;

/**
 * OrderItemTable is a table with an JTextField for ISBN input
 * 
 * @author Administrator
 * 
 */
public class ItemTable<T extends Item> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1106314954455483203L;

	private JTable itemTable;

	private ISBNTextField isbnTextField;

	private ItemController<T> controller;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getItemTable() {
		return itemTable;
	}

	public CommonTableModel getTableModel() {
		return (CommonTableModel) itemTable.getModel();
	}

	public JTextField getIsbnTextField() {
		return isbnTextField;
	}

	public ItemController<T> getController() {
		return controller;
	}

	public void setController(ItemController<T> controller) {
		this.controller = controller;
	}

	public ItemTable(TableCreator creator) {
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(itemTable);
		add(scrollPane, BorderLayout.CENTER);

		// Renderer & Editor
		creator.createTable(itemTable);

		isbnTextField.setCallback(new Callback() {
			@Override
			public void call(Book isbn) {
				controller.add(isbn);
			}

			@Override
			public void call(List<Book> books) {
				for (Book book : books)
					controller.add(book);
			}

			@Override
			public void exception(Exception e) {
				JOptionPane.showMessageDialog(ItemTable.this, e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
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
