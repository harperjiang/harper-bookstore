package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.deliver.DeliveryItem;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.common.ExceptionRunnable;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.ReturnKeyAdapter;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;

public class DOFrame extends JFrame {

	private DeliveryPanel panel;

	private JTextField poNumberField;

	private ItemController<DeliveryItem> doItemController;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public DOFrame() {
		super();

		setTitle("Create Delivery Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 400);

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();

		JLabel poLabel = new JLabel("PO Number");
		topPanel.add(poLabel);

		poNumberField = new JTextField();
		poNumberField.setPreferredSize(new Dimension(200, 25));
		topPanel.add(poNumberField);
		poNumberField.addKeyListener(new ReturnKeyAdapter(
				new ExceptionRunnable() {
					public void run() {
						getController().loadPo();
					}

					public void handleException(final Exception ex) {
						LogManager.getInstance().getLogger(DOFrame.class)
								.error("Exception when saving", ex);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								JOptionPane.showMessageDialog(DOFrame.this,
										ex.getMessage(), "Failed to load",
										JOptionPane.ERROR_MESSAGE);
							}
						});

					}
				}));

		JButton loadPoButton = new JButton("Load");
		loadPoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.loadPo();
			}
		});
		topPanel.add(loadPoButton);

		add(topPanel, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();
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

		panel = new DeliveryPanel();
		tabbedPane.addTab("Delivery Info", panel);

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

		JButton saveAndCloseButton = new JButton("Save and Force Close");
		saveAndCloseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							controller.save(true);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(DOFrame.this,
											"Delivery Order Saved");
								}
							});
						} catch (final Exception ex) {
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
		bottomPanel.add(saveAndCloseButton);

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

}
