package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.deliver.ExpressCompany;
import org.harper.bookstore.domain.deliver.ReceiveItem;
import org.harper.bookstore.domain.deliver.ReceiveOrder;
import org.harper.bookstore.domain.deliver.ReceiveOrder.ReceiveType;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.LabeledTextArea;
import org.harper.bookstore.ui.common.SiteComboBox;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class ROFrame extends JPowerWindowEditor {

	private DeliveryPanel panel;

	private DateTextField createDateField;

	private JComboBox typeCombo;

	private SiteComboBox siteCombo;

	private ItemController<ReceiveItem> roItemController;

	private LabeledTextArea remarkArea;

	private ROController controller;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public ROFrame() {
		super("收/退货登记"); //$NON-NLS-1$
		setSize(800, 500);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		createToolBar(toolBar);

		JPanel centerPane = new JPanel();
		add(centerPane, BorderLayout.CENTER);
		centerPane.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 6, 20, 10));
		topPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		topPanel.add(new JLabel(Messages.getString("ROFrame.label_type"))); //$NON-NLS-1$
		typeCombo = new JComboBox();
		typeCombo.addItem(ReceiveType.RETURN);
		typeCombo.addItem(ReceiveType.RECEIVE);
		typeCombo.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel superLabel = (JLabel) super
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);
				superLabel.setText(((ReceiveType) value).desc());
				return superLabel;
			}
		});
		topPanel.add(typeCombo);

		topPanel.add(new JLabel(Messages.getString("ROFrame.label_create_date"))); //$NON-NLS-1$
		createDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		topPanel.add(createDateField);

		topPanel.add(new JLabel(Messages.getString("ROFrame.label_site"))); //$NON-NLS-1$
		siteCombo = new SiteComboBox(false, false);
		topPanel.add(siteCombo);

		centerPane.add(topPanel, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();
		centerPane.add(tabbedPane, BorderLayout.CENTER);

		panel = new DeliveryPanel();
		tabbedPane.addTab("地址信息", panel);

		remarkArea = new LabeledTextArea("备注"); 
		tabbedPane.addTab(Messages.getString("ROFrame.tab_remark"), remarkArea); //$NON-NLS-1$

		roItemController = new ItemController<ReceiveItem>(null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(ReceiveItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(2, true);

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
			protected ReceiveItem createItem(Book book) {
				ReceiveItem newItem = new ReceiveItem();
				newItem.setBook(book);
				newItem.setCount(1);
				getController().getBean().addItems(newItem);
				return newItem;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new ReceiveItemTableData(item);
			}

		};

		tabbedPane.add("Items", roItemController.getView()); //$NON-NLS-1$

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void createToolBar(JToolBar toolBar) {
		JButton saveButton = new JButton(Messages.getString("ROFrame.btn_save")); //$NON-NLS-1$
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validate = validateBean();
				if (!StringUtils.isEmpty(validate)) {
					JOptionPane.showMessageDialog(
							ROFrame.this.getManagerWindow(), "Validate",
							validate, JOptionPane.ERROR_MESSAGE);
					return;
				}
				new SwingWorker<ReceiveOrder, Object>() {
					@Override
					protected ReceiveOrder doInBackground() throws Exception {
						return getController().save();
					}

					@Override
					protected void done() {
						try {
							getController().setBean(get());
							JOptionPane.showMessageDialog(ROFrame.this
									.getManagerWindow(), Messages
									.getString("ROFrame.msg_save_success")); //$NON-NLS-1$
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									ROFrame.this.getManagerWindow(),
									e.getMessage(),
									Messages.getString("ROFrame.msg_save_error_title"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}.execute();
			}
		});
		toolBar.add(saveButton);

		JButton confirmButton = new JButton(
				Messages.getString("ROFrame.btn_confirm")); //$NON-NLS-1$
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String validate = validateBean();
				if (!StringUtils.isEmpty(validate)) {
					JOptionPane.showMessageDialog(
							ROFrame.this.getManagerWindow(), "Validate",
							validate, JOptionPane.ERROR_MESSAGE);
					return;
				}
				new SwingWorker<ReceiveOrder, Object>() {
					@Override
					protected ReceiveOrder doInBackground() throws Exception {
						return getController().confirm();
					}

					protected void done() {
						try {
							getController().setBean(get());
							JOptionPane.showMessageDialog(ROFrame.this
									.getManagerWindow(), Messages
									.getString("ROFrame.msg_confirm_success")); //$NON-NLS-1$
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									ROFrame.this.getManagerWindow(),
									e.getMessage(),
									Messages.getString("ROFrame.msg_confirm_error_title"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);
						}
					};
				}.execute();
			}
		});
		toolBar.add(confirmButton);
	}

	private String validateBean() {
		if (!(getController().getBean().getCompany() == ExpressCompany.NIL)
				&& StringUtils.isEmpty(getController().getBean().getNumber())) {
			return "快递单号码不能留空";
		}
		return null;
	}

	public DeliveryPanel getPanel() {
		return panel;
	}

	public ItemController<ReceiveItem> getRoItemController() {
		return roItemController;
	}

	public JTextArea getRemarkArea() {
		return remarkArea.getTextField();
	}

	public void setController(ROController controller) {
		this.controller = controller;
	}

	public ROController getController() {
		return controller;
	}

	public DateTextField getCreateDateField() {
		return createDateField;
	}

	public SiteComboBox getSiteCombo() {
		return siteCombo;
	}

	public JComboBox getTypeCombo() {
		return typeCombo;
	}

}
