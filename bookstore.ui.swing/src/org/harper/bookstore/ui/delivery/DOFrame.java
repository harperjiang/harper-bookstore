package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.deliver.DeliveryItem;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.ActionThread;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.common.ChooseDateDialog;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.LabeledTextArea;
import org.harper.bookstore.ui.common.ReturnKeyAdapter;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class DOFrame extends JFrame {

	private DeliveryPanel panel;

	private JTextField poNumberField;

	private JCheckBox missedCheck;

	private ItemController<DeliveryItem> doItemController;

	private LabeledTextArea remarkArea;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public DOFrame() {
		super();

		setTitle("Create Delivery Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();

		JLabel poLabel = new JLabel("PO Number");
		topPanel.add(poLabel);

		final JTabbedPane tabbedPane = new JTabbedPane();
		final ActionThread loadAction = new ActionThread() {
			public void execute() {
				getController().loadPo();
			}

			public void success() {
				if (getMissedCheck().isSelected()) {
					tabbedPane.setSelectedIndex(1);
				}
			}

			public void exception(final Exception ex) {
				LogManager.getInstance().getLogger(DOFrame.class)
						.error("Exception when saving", ex);
				JOptionPane.showMessageDialog(DOFrame.this, ex.getMessage(),
						"Failed to load", JOptionPane.ERROR_MESSAGE);
			}
		};

		poNumberField = new JTextField();
		poNumberField.setPreferredSize(new Dimension(200, 25));
		topPanel.add(poNumberField);
		poNumberField.addKeyListener(new ReturnKeyAdapter(loadAction));

		JButton loadPoButton = new JButton("Load");
		loadPoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new Thread(loadAction).start();
			}
		});
		topPanel.add(loadPoButton);

		missedCheck = new JCheckBox("Send Missed");
		topPanel.add(missedCheck);

		add(topPanel, BorderLayout.NORTH);

		add(tabbedPane, BorderLayout.CENTER);

		doItemController = new ItemController<DeliveryItem>(null,
				new TableCreator() {

					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(DeliveryItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(3, true);

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
			protected DeliveryItem createItem(Book book) {
				DeliveryItem newItem = new DeliveryItem();
				OrderItem oitem = new OrderItem();
				oitem.setBook(book);
				newItem.setOrderItem(oitem);
				return newItem;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new DeliveryItemTableData(item);
			}

		};

		tabbedPane.add("Item Info", doItemController.getView());

		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(2, 1));
		panel = new DeliveryPanel();
		secondPanel.add(panel);
		tabbedPane.addTab("Delivery Info", secondPanel);

		remarkArea = new LabeledTextArea("Remark");
		secondPanel.add(remarkArea);

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							controller.save(false);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(DOFrame.this,
											"Delivery Order Saved");
								}
							});
						} catch (final Exception ex) {
							ex.printStackTrace();
							LogManager.getInstance().getLogger(DOFrame.class)
									.error("Error on saving", ex);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(DOFrame.this,
											ex.getMessage(), "Failed to save",
											JOptionPane.ERROR_MESSAGE);
								}
							});
						}
					}
				}).start();
			}
		});
		bottomPanel.add(saveButton);

		JButton saveAndSendButton = new JButton("Save and Send");
		saveAndSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							ChooseDateDialog dialog = new ChooseDateDialog(
									DOFrame.this, "Choose Date",
									"Please choose send date", new Date());

							controller.save(dialog.getBean().getDate(), true);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(DOFrame.this,
											"Delivery Order Saved and Sent");
								}
							});
						} catch (final Exception ex) {
							ex.printStackTrace();
							LogManager.getInstance().getLogger(DOFrame.class)
									.error("Error on saving", ex);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(DOFrame.this,
											ex.getMessage(), "Failed to save",
											JOptionPane.ERROR_MESSAGE);
								}
							});
						}
					}
				}).start();
			}
		});
		bottomPanel.add(saveAndSendButton);

		setVisible(true);
	}

	private DOController controller;

	public DOController getController() {
		return controller;
	}

	public void setController(DOController controller) {
		this.controller = controller;
	}

	public DeliveryPanel getPanel() {
		return panel;
	}

	public JTextField getPoNumberField() {
		return poNumberField;
	}

	public ItemController<DeliveryItem> getDoItemController() {
		return doItemController;
	}

	public JCheckBox getMissedCheck() {
		return missedCheck;
	}

	public JTextArea getRemarkArea() {
		return remarkArea.getTextField();
	}

}
