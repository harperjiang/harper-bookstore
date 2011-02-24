package org.harper.bookstore.ui.profile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.springframework.util.CollectionUtils;

public class BookManageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4368824817075660551L;

	JTable bookTable;

	ISBNTextField isbnTextField;

	BookManagerController controller;

	public BookManageFrame() {
		super();
		setTitle("Book Manage");
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
				JOptionPane.showMessageDialog(BookManageFrame.this, "Saved");
			}
		});

		JButton newButton = new JButton("Add Book");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewBookDialog dialog = new NewBookDialog(BookManageFrame.this);
				if (dialog.isOkay()) {
					Book added = dialog.getAdded();
					getController().add(added);
				}
			}
		});
		toolBar.add(saveButton);
		toolBar.add(newButton);
		add(toolBar, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);

		JPanel headerPanel = new JPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new FlowLayout());

		isbnTextField = new ISBNTextField();
		isbnTextField.setPreferredSize(new Dimension(200, 20));
		isbnTextField.setCallback(new Callback() {

			@Override
			public void call(Book isbn) {
				controller.select(isbn);
			}

			@Override
			public void call(List<Book> books) {
				if (CollectionUtils.isEmpty(books) || books.size() > 1) {
					return;
				}
				for (Book book : books)
					call(book);
			}

			@Override
			public void exception(Exception e) {
				JOptionPane.showMessageDialog(BookManageFrame.this,
						e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		});
		headerPanel.add(isbnTextField);

		bookTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(BookInfoTableData.class);
		ctm.setCellEditable(0, true);
		ctm.setCellEditable(1, true);
		ctm.setCellEditable(2, true);
		ctm.setCellEditable(3, true);
		ctm.setAutoAdd(false);
		bookTable.setModel(ctm);

		bookTable.setDefaultRenderer(String.class,
				new DefaultTableCellRenderer());
		bookTable.setDefaultEditor(String.class, new DefaultCellEditor(
				new JTextField()));
		bookTable.setDefaultEditor(BigDecimal.class, new DefaultCellEditor(
				new NumTextField()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bookTable);

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public BookManagerController getController() {
		return controller;
	}

}
