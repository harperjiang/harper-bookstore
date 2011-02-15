package org.harper.bookstore.ui.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.profile.BookInfoTableData;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

/**
 * A Dialog that allows multiple selection of books
 * 
 * @author Administrator
 * 
 */
public class ISBNChooseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3402829402319577297L;

	private JTable bookTable;

	private JButton okButton;

	private JButton cancelButton;

	private ISBNChooseBean bean;

	public ISBNChooseDialog(JFrame parent) {
		super(parent, true);

		setTitle("Choose Book");
		setSize(400, 450);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		bookTable = new JTable();
		bookTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(BookInfoTableData.class);
		bookTable.setModel(ctm);
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					select();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(bookTable);
		add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		add(bottomPanel, BorderLayout.SOUTH);

		okButton = new JButton("  OK  ");
		bottomPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				select();
			}
		});

		cancelButton = new JButton("Cancel");
		bottomPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ISBNChooseDialog.this.setResult(JOptionPane.CANCEL_OPTION);
				ISBNChooseDialog.this.dispose();
			}
		});

		initManager();
	}

	protected void select() {
		int[] selecteds = bookTable.getSelectedRows();
		if (selecteds.length == 0) {
			JOptionPane.showMessageDialog(ISBNChooseDialog.this,
					"Please select a book", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		List<Book> books = new ArrayList<Book>();
		for (int selected : selecteds) {
			Book book = (Book) ((BookInfoTableData) (((CommonTableModel) bookTable
					.getModel()).getData().get(selected))).getBean();
			books.add(book);
		}
		bean.setSelected(books);
		ISBNChooseDialog.this.setResult(JOptionPane.OK_OPTION);
		ISBNChooseDialog.this.dispose();
	}

	protected void initManager() {
		bean = new ISBNChooseBean();
		manager = new BindingManager(bean);

		manager.addBinding(new TableBinding(bookTable, "bookList"));

	}

	private BindingManager manager;

	public JTable getBookTable() {
		return bookTable;
	}

	public ISBNChooseBean getBean() {
		return bean;
	}

	private int result = -1;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
