package org.harper.bookstore.ui.library;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.library.RecordItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class ReturnBookFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6708088761688989161L;

	private ReturnBookController controller;

	private JTextField borrowerNameField;

	private JComboBox siteCombo;

	private DateTextField accountDateField;

	private ItemController<RecordItem> itemController;

	public ReturnBookFrame(ReturnBookController controller) {
		super();
		setTitle("Return Book");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);

		toolBar.add(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getController().save();
					JOptionPane.showMessageDialog(ReturnBookFrame.this,
							"Record Saved");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(ReturnBookFrame.this, ee
							.getMessage(), "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);

		centerPanel.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());

		borrowerNameField = new JTextField();
		borrowerNameField.setPreferredSize(new Dimension(120, 20));
		borrowerNameField.setEditable(true);

		headerPanel.add(new JLabel("Borrower"));
		headerPanel.add(borrowerNameField);

		headerPanel.add(new JLabel("To Site"));
		siteCombo = new JComboBox();
		siteCombo.setPreferredSize(new Dimension(120, 20));
		siteCombo.setRenderer(new SiteListRenderer());
		headerPanel.add(siteCombo);

		JButton findButton = new JButton("Find Borrower");
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindBorrowerController fbc = new FindBorrowerController(
						new FindBorrowerDialog(ReturnBookFrame.this));
				if (null != fbc.getBean().getSelected()) {
					getController().getBean().setBorrower(
							fbc.getBean().getSelected());
					getController().updateData();
				}
			}
		});
		headerPanel.add(findButton);

		headerPanel.add(new JLabel("Accounting Date"));
		accountDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		accountDateField.setPreferredSize(new Dimension(120, 20));
		headerPanel.add(accountDateField);

		centerPanel.add(headerPanel, BorderLayout.NORTH);

		itemController = new ItemController<RecordItem>(null,
				new TableCreator() {

					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(RecordItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(2, true);
						table.setDefaultRenderer(Integer.TYPE,
								new DefaultTableCellRenderer());
						table.setDefaultEditor(Integer.TYPE,
								new DefaultCellEditor(new NumTextField()));
					}
				}) {

			@Override
			protected RecordItem createItem(Book book) {
				return new RecordItem();
			}

			@Override
			protected TableData createTableData(Object item) {
				return new RecordItemTableData(item);
			}
		};

		centerPanel.add(itemController.getView(), BorderLayout.CENTER);

		setController(controller);
		this.setController(controller);
		for (StoreSite site : getController().getAvailableSite()) {
			getSiteCombo().addItem(site);
		}
		
		setVisible(true);
	}

	public ReturnBookController getController() {
		return controller;
	}

	public void setController(ReturnBookController controller) {
		this.controller = controller;
	}

	public ItemController<RecordItem> getItemController() {
		return itemController;
	}

	public JTextField getBorrowerNameField() {
		return borrowerNameField;
	}

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

	public DateTextField getAccountDateField() {
		return accountDateField;
	}

}
