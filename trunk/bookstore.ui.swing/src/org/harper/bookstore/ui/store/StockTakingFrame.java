package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockTakingItem;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.ui.common.ActionThread;
import org.harper.bookstore.ui.common.ChooseDateDialog;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.NegAmtRedTableRenderer;
import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class StockTakingFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6337181539452665666L;

	private StockTakingController controller;

	private JComboBox siteCombo;

	private DateTextField createDateField;
	
	private JTextField numberField;

	private JTextField statusField;

	private ItemController<StockTakingItem> itemController;

	public StockTakingFrame(StockTakingController controller) {
		super("Stock Taking");
		setSize(800, 600);
		
		// setResizable(false);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();

		add(toolBar, BorderLayout.NORTH);

		JButton saveButton = new JButton("Save");
		toolBar.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new ActionThread() {
					@Override
					public void execute() {
						getController().save();
					}

					@Override
					public void success() {
						JOptionPane.showMessageDialog(StockTakingFrame.this,
								"Success");
					}

					@Override
					public void exception(Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(StockTakingFrame.this,
								ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}).start();
			}
		});

		JButton confirmButton = new JButton("Confirm");
		toolBar.add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new ActionThread() {
					@Override
					public void execute() {
						getController().save();
						ChooseDateDialog dd = new ChooseDateDialog(
								StockTakingFrame.this.getManagerWindow(), "Confirm Date",
								"Input Confirm Date", new Date());
						if (dd.isOkay())
							getController().confirm(dd.getBean().getDate());
					}

					@Override
					public void success() {
						JOptionPane.showMessageDialog(StockTakingFrame.this,
								"Success");
					}

					@Override
					public void exception(Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(StockTakingFrame.this,
								ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}).start();
			}
		});

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		headerPanel.setLayout(new GridLayout(2, 4, 5, 5));

		JLabel siteLabel = new JLabel("Store Site:");
		headerPanel.add(siteLabel);

		siteCombo = new JComboBox();
		siteCombo.setRenderer(new SiteListRenderer());
		headerPanel.add(siteCombo);

		JLabel createDateLabel = new JLabel("Create Date");
		headerPanel.add(createDateLabel);

		createDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		headerPanel.add(createDateField);

		JLabel numberLabel = new JLabel("Number");
		headerPanel.add(numberLabel);
		
		numberField = new JTextField();
		numberField.setEditable(false);
		headerPanel.add(numberField);
		
		JLabel statusLabel = new JLabel("Status");
		headerPanel.add(statusLabel);

		statusField = new JTextField();
		statusField.setEditable(false);
		headerPanel.add(statusField);

		// Create Table;
		itemController = new ItemController<StockTakingItem>(null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						final CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(StockTakingItemTableData.class);
						ctm.setCellEditable(3, true);
						table.setModel(ctm);

						table.setDefaultRenderer(BigDecimal.class,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(Integer.TYPE,
								new DefaultTableCellRenderer());
						table.getColumnModel().getColumn(5)
								.setCellRenderer(new NegAmtRedTableRenderer());
						table.setDefaultEditor(Integer.TYPE,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(BigDecimal.class,
								new DefaultCellEditor(new NumTextField()));

						table.getModel().addTableModelListener(
								new TableModelListener() {
									@Override
									public void tableChanged(TableModelEvent e) {
										System.out.println(e.getColumn());
										if (3 == e.getColumn())
											ctm.fireTableRowsUpdated(
													e.getFirstRow(),
													e.getLastRow());
									}
								});
					}
				}) {

			@Override
			protected StockTakingItem createItem(Book book) {
				StockTakingItem item = new StockTakingItem();
				item.setBook(book);
				item.setCount(1);
				getController().fillItem(item);
				return item;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new StockTakingItemTableData(item);
			}

		};

		centerPanel.add(itemController.getView(), BorderLayout.CENTER);

		this.controller = controller;

		for (StoreSite site : getController().getSites()) {
			siteCombo.addItem(site);
		}
	}

	public StockTakingController getController() {
		return controller;
	}

	public JComboBox getSiteCombo() {
		return siteCombo;
	}

	public DateTextField getCreateDateField() {
		return createDateField;
	}

	public JTextField getStatusField() {
		return statusField;
	}

	public ItemController<StockTakingItem> getItemController() {
		return itemController;
	}

	public JTextField getNumberField() {
		return numberField;
	}

}
