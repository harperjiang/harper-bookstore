package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.ui.common.ISBNTextField;
import org.harper.bookstore.ui.common.ISBNTextField.Callback;
import org.harper.bookstore.ui.common.SiteComboBox;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.gui.swing.comp.textfield.IntTextField;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;
import org.springframework.util.CollectionUtils;

public class NewStockAlertDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5461682951042947998L;

	private boolean okay;

	private StockAlert bean;

	private BindingManager manager;

	private ISBNTextField bookField;

	private JTextField bookIsbnField;

	private JTextField bookNameField;

	private JComboBox siteCombo;

	private IntTextField warnThresholdField;

	private IntTextField errorThresholdField;

	public NewStockAlertDialog(JFrame parent, StockAlert newbean) {
		super(parent);
		setTitle("Stock Alert");
		setSize(250, 300);
		setResizable(false);
		setModal(true);
		UIStandard.standardDialog(this);

		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(5, 2, 10, 20));
		centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(centerPanel, BorderLayout.CENTER);

		bookField = new ISBNTextField();
		bookField.setCallback(new Callback() {
			@Override
			public void call(Book book) {
				bean.setBook(book);
				manager.loadAll();
			}

			@Override
			public void call(List<Book> books) {
				if (!CollectionUtils.isEmpty(books) && books.size() == 1)
					call(books.get(0));
				else
					throw new IllegalArgumentException(
							"Cannot handle multiple books");
			}

			@Override
			public void exception(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(NewStockAlertDialog.this,
						e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		});
		bookField.setBorder(new EmptyBorder(20, 20, 20, 20));
		add(bookField, BorderLayout.NORTH);

		centerPanel.add(new JLabel("Book ISBN"));
		bookIsbnField = new JTextField();
		bookIsbnField.setEditable(false);
		centerPanel.add(bookIsbnField);

		centerPanel.add(new JLabel("Book Name"));
		bookNameField = new JTextField();
		bookNameField.setEditable(false);
		centerPanel.add(bookNameField);

		centerPanel.add(new JLabel("Site"));
		siteCombo = new SiteComboBox(true, false);
		centerPanel.add(siteCombo);

		centerPanel.add(new JLabel("Warn Threshold"));
		warnThresholdField = new IntTextField();
		centerPanel.add(warnThresholdField);

		centerPanel.add(new JLabel("Error Threshold"));
		errorThresholdField = new IntTextField();
		centerPanel.add(errorThresholdField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton saveButton = new JButton("Save");
		buttonPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Validate
				String msg = null;
				if (!StringUtils.isEmpty(msg = validateBean())) {
					JOptionPane
							.showMessageDialog(NewStockAlertDialog.this, msg,
									"Validation Failed",
									JOptionPane.ERROR_MESSAGE);
					return;
				}
				okay = true;
				NewStockAlertDialog.this.dispose();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewStockAlertDialog.this.dispose();
			}
		});

		add(buttonPanel, BorderLayout.SOUTH);

		this.bean = newbean == null ? new StockAlert() : newbean;
		initManager();

		setVisible(true);
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(new JTextBinding(bookIsbnField, "book.isbn"));
		manager.addBinding(new JTextBinding(bookNameField, "book.name"));
		manager.addBinding(warnThresholdField.new NumTextBinding(
				"warnThreshold"));
		manager.addBinding(errorThresholdField.new NumTextBinding(
				"errorThreshold"));

		manager.loadAll();
	}

	public boolean isOkay() {
		return okay;
	}

	public void setOkay(boolean okay) {
		this.okay = okay;
	}

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

	public ISBNTextField getBookField() {
		return bookField;
	}

	public IntTextField getWarnThresholdField() {
		return warnThresholdField;
	}

	public IntTextField getErrorThresholdField() {
		return errorThresholdField;
	}

	public JTextField getBookIsbnField() {
		return bookIsbnField;
	}

	public JTextField getBookNameField() {
		return bookNameField;
	}

	protected String validateBean() {
		if (bean.getWarnThreshold() <= 0)
			return "Warn Threshold should be greater than 0";
		if (bean.getErrorThreshold() <= 0)
			return "Error Threshold should be greater than 0";
		if (bean.getWarnThreshold() < bean.getErrorThreshold())
			return "Warning Threshold should be greater than Error Threshold";
		return null;
	}

	public StockAlert getBean() {
		return bean;
	}
}
