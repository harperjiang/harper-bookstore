package org.harper.bookstore.ui.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.LabeledTextArea;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class POFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883588327069169379L;

	private JButton saveButton;

	private JButton printButton;

	private JButton printExpressButton;

	private JButton confirmButton;

	private JButton sendButton;

	private JButton partialSendButton;

	private JButton cancelButton;

	private ItemController<OrderItem> itemController;

	private JTable dispItemTable;

	private POHeaderPanel headerPanel;

	private POController controller;

	private JLabel subtotalAmountLabel;

	private NumTextField feeAmountField;

	private LabeledTextArea remarkArea;

	private LabeledTextArea memoArea;

	private DeliveryPanel deliveryPanel;

	public POFrame(POController controller) {
		super();
		setTitle(Messages.getString("POFrame.title")); //$NON-NLS-1$
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		// Create Tool Bar
		createToolBar();

		createBody();

		this.controller = controller;

		StoreSite defSite = null;
		for (StoreSite site : getController().getAvailableSite()) {
			headerPanel.getSiteCombo().addItem(site);
			if (defSite == null || site.getPrivilege() < defSite.getPrivilege())
				defSite = site;
		}
		if (null != defSite)
			headerPanel.getSiteCombo().setSelectedItem(defSite);

		setVisible(true);
	}

	protected void createToolBar() {
		JToolBar toolBar = new JToolBar();

		saveButton = new JButton(Messages.getString("POFrame.saveButton")); //$NON-NLS-1$
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.save();
					JOptionPane.showMessageDialog(POFrame.this,
							Messages.getString("POFrame.saveMsg")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(POFrame.this,
							ee.getMessage(),
							Messages.getString("POFrame.exception"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(saveButton);

		printButton = new JButton(Messages.getString("POFrame.printButton")); //$NON-NLS-1$
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread() {
					public void run() {
						try {
							controller.print();
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane
											.showMessageDialog(
													POFrame.this,
													Messages.getString("POFrame.printMsg")); //$NON-NLS-1$
								}
							});
						} catch (final Exception ee) {
							LogManager
									.getInstance()
									.getLogger(POFrame.class)
									.error(Messages
											.getString("POFrame.printError"), ee); //$NON-NLS-1$
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(
											POFrame.this,
											ee.getMessage(),
											Messages.getString("POFrame.exception"), //$NON-NLS-1$
											JOptionPane.ERROR_MESSAGE);
								}
							});

						}
					}
				}.start();

			}
		});
		toolBar.add(printButton);

		printExpressButton = new JButton(
				Messages.getString("POFrame.printExpress")); //$NON-NLS-1$
		printExpressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread() {
					public void run() {
						try {
							controller.printExpress();
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane
											.showMessageDialog(
													POFrame.this,
													Messages.getString("POFrame.printExpressMsg")); //$NON-NLS-1$
								}
							});
						} catch (final Exception ee) {
							LogManager
									.getInstance()
									.getLogger(POFrame.class)
									.error(Messages
											.getString("POFrame.printExpressError"), ee); //$NON-NLS-1$
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(
											POFrame.this,
											ee.getMessage(),
											Messages.getString("POFrame.exception"), //$NON-NLS-1$
											JOptionPane.ERROR_MESSAGE);
								}
							});

						}
					}
				}.start();

			}
		});
		toolBar.add(printExpressButton);

		confirmButton = new JButton(Messages.getString("POFrame.confirmButton")); //$NON-NLS-1$
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.confirm();
					JOptionPane.showMessageDialog(POFrame.this,
							Messages.getString("POFrame.confirmMsg")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(POFrame.this,
							ee.getMessage(),
							Messages.getString("POFrame.exception"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(confirmButton);

		sendButton = new JButton(Messages.getString("POFrame.sendButton")); //$NON-NLS-1$
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.send();
					JOptionPane.showMessageDialog(POFrame.this,
							Messages.getString("POFrame.sendMsg")); //$NON-NLS-1$
				} catch (Exception ee) {
					LogManager.getInstance().getLogger(getClass())
							.error(Messages.getString("POFrame.sendError"), ee); //$NON-NLS-1$
					JOptionPane.showMessageDialog(POFrame.this,
							ee.getMessage(),
							Messages.getString("POFrame.exception"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(sendButton);

		partialSendButton = new JButton(
				Messages.getString("POFrame.partialSendButton")); //$NON-NLS-1$
		partialSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.partialSend();
					JOptionPane.showMessageDialog(POFrame.this,
							Messages.getString("POFrame.sendMsg")); //$NON-NLS-1$
				} catch (Exception ee) {
					LogManager.getInstance().getLogger(getClass())
							.error(Messages.getString("POFrame.sendError"), ee); //$NON-NLS-1$
					JOptionPane.showMessageDialog(POFrame.this,
							ee.getMessage(),
							Messages.getString("POFrame.exception"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(partialSendButton);

		cancelButton = new JButton(Messages.getString("POFrame.cancelButton")); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.cancel();
					JOptionPane.showMessageDialog(POFrame.this,
							Messages.getString("POFrame.cancelMsg")); //$NON-NLS-1$
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(POFrame.this,
							ee.getMessage(),
							Messages.getString("POFrame.exception"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		toolBar.add(cancelButton);

		toolBar.setFloatable(false);

		add(toolBar, BorderLayout.NORTH);
	}

	protected void createBody() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		headerPanel = new POHeaderPanel();
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		JTabbedPane tab = new JTabbedPane();

		centerPanel.add(tab, BorderLayout.CENTER);

		itemController = new ItemController<OrderItem>((List<OrderItem>) null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(AgentOrderItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(2, true);
						ctm.setCellEditable(3, true);
						ctm.setCellEditable(5, false);
						ctm.setCellEditable(4, true);

						table.setDefaultRenderer(Integer.TYPE,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(BigDecimal.class,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(Boolean.TYPE,
								new CheckBoxTableRenderer());
						table.setDefaultEditor(Integer.TYPE,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(BigDecimal.class,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(Boolean.TYPE,
								new DefaultCellEditor(new JCheckBox()));
					}

				}) {

			@Override
			protected OrderItem createItem(Book book) {
				OrderItem item = new OrderItem();
				item.setBook(book);
				item.setUnitPrice(getController().getListPrice(book));
				item.setCount(1);
				return item;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new AgentOrderItemTableData(item);
			}
		};
		tab.add(Messages.getString("POFrame.itemTab"), itemController.getView()); //$NON-NLS-1$
		// Init tables;
		dispItemTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(DisplayItemTableData.class);
		dispItemTable.setModel(ctm);

		dispItemTable.setDefaultRenderer(Integer.TYPE,
				new DefaultTableCellRenderer());
		dispItemTable.setDefaultRenderer(BigDecimal.class,
				new DefaultTableCellRenderer());
		dispItemTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		dispItemTable.getColumnModel().getColumn(0).setPreferredWidth(600);
		dispItemTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		dispItemTable.getColumnModel().getColumn(2).setPreferredWidth(60);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(dispItemTable);
		tab.add(Messages.getString("POFrame.dispItemTab"), scrollPane); //$NON-NLS-1$

		deliveryPanel = new DeliveryPanel();

		tab.add(Messages.getString("POFrame.deliveryTab"), deliveryPanel); //$NON-NLS-1$

		JPanel feePanel = new JPanel();
		feePanel.add(new JLabel(Messages.getString("POFrame.addFee"))); //$NON-NLS-1$

		feeAmountField = new NumTextField();
		feeAmountField.setPreferredSize(new Dimension(150, 20));
		feePanel.add(feeAmountField);
		centerPanel.add(feePanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		JPanel stPanel = new JPanel();
		stPanel.setLayout(new FlowLayout());

		JLabel subtotalLabel = new JLabel(
				Messages.getString("POFrame.subtotal")); //$NON-NLS-1$
		stPanel.add(subtotalLabel, BorderLayout.NORTH);

		subtotalAmountLabel = new JLabel();
		subtotalAmountLabel.setPreferredSize(new Dimension(100, 20));
		stPanel.add(subtotalAmountLabel);

		bottomPanel.add(stPanel, BorderLayout.NORTH);

		JPanel bottomCenterPanel = new JPanel();
		bottomCenterPanel.setLayout(new GridLayout(2,1));

		remarkArea = new LabeledTextArea(Messages.getString("POFrame.remark")); //$NON-NLS-1$
		remarkArea.setPreferredSize(new Dimension(80, 100));
		remarkArea.getTextField().setEditable(false);
		remarkArea.getTextField().setEnabled(false);
		bottomCenterPanel.add(remarkArea);
		
		memoArea = new LabeledTextArea("Memo");
		memoArea.setPreferredSize(new Dimension(80, 100));
		bottomCenterPanel.add(memoArea);
		
		bottomPanel.add(bottomCenterPanel, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ItemController<OrderItem> getItemController() {
		return itemController;
	}

	public POHeaderPanel getHeaderPanel() {
		return headerPanel;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getPrintButton() {
		return printButton;
	}

	public POController getController() {
		return controller;
	}

	protected void setController(POController controller) {
		this.controller = controller;
	}

	public JLabel getSubtotalAmountLabel() {
		return subtotalAmountLabel;
	}

	public NumTextField getFeeAmountField() {
		return feeAmountField;
	}

	public JTextArea getRemarkArea() {
		return remarkArea.getTextField();
	}

	public JTable getDispItemTable() {
		return dispItemTable;
	}

	public DeliveryPanel getDeliveryPanel() {
		return deliveryPanel;
	}

	public JTextArea getMemoArea() {
		return memoArea.getTextField();
	}

}
