package org.harper.bookstore.ui.common;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;
import org.harper.frm.ValidateException;
import org.springframework.util.CollectionUtils;

/**
 * This TextField will pop up a list of book for choice
 *
 */
public class ISBNTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8402126141187155414L;

	public ISBNTextField() {
		super();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ProfileService ps = new ProfileService();
				if ('\n' == e.getKeyChar()) {
					if (null != callback)
						try {
							String possibleISBN = null == getText() ? null
									: getText().trim();
							List<Book> books = ps.findBooks(possibleISBN);
							if (CollectionUtils.isEmpty(books)) {
								try {
									Long.parseLong(possibleISBN);
								} catch (NumberFormatException eeee) {
									callback.exception(ValidateException
											.noSuchBook(possibleISBN));
									return;
								}
								String res = JOptionPane
										.showInputDialog("Please input the book name to create a new book");
								if (StringUtils.isEmpty(res)) {
									callback.exception(ValidateException
											.noSuchBook(possibleISBN));
									return;
								} else {
									Book newBook = new Book();
									newBook.setIsbn(possibleISBN);
									newBook.setName(res);
									callback.call(ps.newBook(newBook));
									return;
								}
							}
							if (1 == books.size()) {
								callback.call(books.get(0));
								return;
							} else {
								Component comp = ISBNTextField.this;
								while (!(comp instanceof JFrame))
									comp = comp.getParent();
								ISBNChooseDialog dialog = new ISBNChooseDialog(
										(JFrame) comp);
								dialog.getBean().setBookList(books);
								dialog.setVisible(true);
								if (JOptionPane.OK_OPTION == dialog.getResult()) {
									callback.call(dialog.getBean()
											.getSelected());
									return;
								}
							}
						} catch (Exception e1) {
							callback.exception(e1);
						} finally {
							setText(null);
						}
				}
			}
		});
	}

	private Callback callback;

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public static interface Callback {

		public void call(Book book);
		
		public void call(List<Book> books);

		public void exception(Exception e);
	}
}
