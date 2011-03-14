package org.harper.bookstore.ui.common;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.service.ProfileService;

public class ISBNTextField2 extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8402126141187155414L;

	public ISBNTextField2() {
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
							List<Book> books = ps.powerFindBooks(possibleISBN);

							callback.call(books);

						} catch (Exception e1) {
							callback.exception(e1);
						} finally {
							setText(null);
						}
				}
			}
		});
	}

	private Callback2 callback;

	public Callback2 getCallback() {
		return callback;
	}

	public void setCallback(Callback2 callback) {
		this.callback = callback;
	}

	public static interface Callback2 {

		public void call(List<Book> book);

		public void exception(Exception e);
	}
}
