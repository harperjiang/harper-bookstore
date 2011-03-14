package org.harper.bookstore.ui.profile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.BookSet;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.bookstore.ui.common.LabeledTextField;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class BookSetManageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1510538477354878177L;

	JTable bookTable;

	LabeledTextField isbnTextField;

	LabeledTextField nameTextField;

	ISBNTextField findBookField;

	BookSetManageController controller;

	public BookSetManageFrame() {
		super();
		setTitle("Book Set Manage");
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
			}
		});

		toolBar.add(saveButton);
		add(toolBar, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);

		JPanel headerPanel = new JPanel();

		headerPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		GridLayout layout = new GridLayout(4, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		headerPanel.setLayout(layout);

		isbnTextField = new LabeledTextField("Book Set ISBN:");
		headerPanel.add(isbnTextField);

		nameTextField = new LabeledTextField("Book Set Name:");
		headerPanel.add(nameTextField);

		headerPanel.add(new JLabel("Input ISBN or book name to choose book"));

		findBookField = new ISBNTextField();
		findBookField.setPreferredSize(new Dimension(200, 20));
		findBookField.setCallback(new Callback() {

			@Override
			public void call(Book book) {
				if (book instanceof BookSet)
					return;
				controller.add(book);
			}

			@Override
			public void call(List<Book> books) {
				for (Book book : books)
					call(book);
			}

			@Override
			public void exception(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(BookSetManageFrame.this,
						e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		});
		headerPanel.add(findBookField);

		bookTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(BookSetItemTableData.class);
		ctm.setAutoAdd(false);
		ctm.setCellEditable(2, true);
		bookTable.setModel(ctm);

		bookTable.setDefaultRenderer(String.class,
				new DefaultTableCellRenderer());
		bookTable.setDefaultEditor(String.class, new DefaultCellEditor(
				new JTextField()));
		bookTable.setDefaultRenderer(BigDecimal.class,
				new DefaultTableCellRenderer());
		bookTable.setDefaultEditor(BigDecimal.class, new DefaultCellEditor(
				new NumTextField()));

		bookTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (127 == e.getKeyChar() && -1 != bookTable.getSelectedRow()) {
					controller.remove(bookTable.getSelectedRow());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bookTable);

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getBookTable() {
		return bookTable;
	}

	public JTextField getIsbnTextField() {
		return isbnTextField.getTextField();
	}

	public JTextField getNameTextField() {
		return nameTextField.getTextField();
	}

	public ISBNTextField getFindBookField() {
		return findBookField;
	}

	public BookSetManageController getController() {
		return controller;
	}

	public void setController(BookSetManageController controller) {
		this.controller = controller;
	}

}
