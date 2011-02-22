package org.harper.bookstore.ui.profile;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class NewBookDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055849990441811615L;

	private JTextField isbnTextField;

	private JTextField bookTextField;

	private JTextArea descArea;

	private JButton saveButton;

	public NewBookDialog(JFrame parent) {
		super(parent, true);
		setTitle("New Book");
		setSize(400, 480);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setPreferredSize(new Dimension(80, 20));
		add(isbnLabel);

		isbnTextField = new JTextField();
		isbnTextField.setPreferredSize(new Dimension(300, 20));
		add(isbnTextField);

		JLabel bookNameLabel = new JLabel("Book Name");
		bookNameLabel.setPreferredSize(new Dimension(80, 20));
		add(bookNameLabel);

		bookTextField = new JTextField();
		bookTextField.setPreferredSize(new Dimension(300, 20));
		add(bookTextField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(390, 300));
		descArea = new JTextArea();
		scrollPane.setViewportView(descArea);
		add(scrollPane);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Book book = new Book();
				book.setIsbn(bean.getIsbn());
				book.setName(bean.getBookName());
				book.setDesc(bean.getDesc());
				NewBookDialog.this.added = new ProfileService().newBook(book);
				NewBookDialog.this.dispose();
			}
		});
		add(saveButton);

		bean = new NewBookBean();
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(isbnTextField, "isbn"));
		manager.addBinding(new JTextBinding(bookTextField, "bookName"));
		manager.addBinding(new JTextBinding(descArea, "desc"));

		setVisible(true);
	}

	private NewBookBean bean;

	private Book added;

	private BindingManager manager;

	public JTextField getIsbnTextField() {
		return isbnTextField;
	}

	public JTextField getBookTextField() {
		return bookTextField;
	}

	public JTextArea getDescArea() {
		return descArea;
	}

	public static void main(String[] args) {
		new NewBookDialog(null).setVisible(true);
	}

	public Book getAdded() {
		return added;
	}
}
